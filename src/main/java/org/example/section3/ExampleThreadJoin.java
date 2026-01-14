package org.example.section3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleThreadJoin {

    /*
    * In this example we calculate the factorial for each number in a list
    * We will show how to use join when 1 thread is dependent on another
    * and when the depending thread should sleep until its time for it to do
    * work.
    *
    * */
    public void executorThreadJoin() throws InterruptedException {
        List<Long> inputNums = Arrays.asList(0L,34L,3L,2L,12L,6L,12L);

        List<FactorialThread> threads = new ArrayList<>();

        // Create a new thread to calculate factorial for each num
        for(long num : inputNums)
            threads.add(new FactorialThread(num));

        //  problematic(inputNums, threads);
        fixed(inputNums, threads);
    }

    public void problematic(List<Long> inputNums, List<FactorialThread> threads)
    {
       /*
        *
        * Output Example:

            Calculation for  3425 is still in progress.
            Calculation for  35235 is still in progress.
            Calculation for  2342 is still in progress.
            Calculation for  1234 is still in progress.
            Calculation for  687 is still in progress.
            Factorial of 12 is 479001600
            Factorial Join Task End

            Process finished with exit code 0
        * */

        /*
        * You can see the main thread is already checking for the result
        * while the Factorial Threads are still in progress
        *
        * This is actually a race condition
        *
        * Both the main thread and the FactorialThreads are racing
        * independently to their results without waiting for each other
        * so we cant really predicate whether main or those FactorialThreads
        * will finish first
        *
        * */


        // Start each thread
        for(Thread thread: threads)
            thread.start();

        /*
        In the main thread, we want to print out whether the thread
        has completed calculating the factorial or not
        */
        for(int i = 0; i < inputNums.size(); i++)
        {
            FactorialThread t = threads.get(i);
            if(t.isFinished())
                System.out.println("Factorial of " + inputNums.get(i) + " is "+ t.getResult());
            else
                System.out.println("Calculation for  " + inputNums.get(i) + " is still in progress.");

        }
    }

    public void fixed(List<Long> inputNums, List<FactorialThread> threads) throws InterruptedException {
                /*
        *
        * Output Example:
        * Good this proves factorials were calculated for all and then the main
        * printed them!

            Factorial Join Task Start
            Factorial of 0 is 1
            Factorial of 34 is 295232799039604140847618609643520000000
            Factorial of 3 is 6
            Factorial of 2 is 2
            Factorial of 12 is 479001600
            Factorial of 6 is 720
            Factorial of 12 is 479001600
            Factorial Join Task End
            Disconnected from the target VM, address: '127.0.0.1:65163', transport: 'socket'

            Process finished with exit code 0
        * */

        /*
         * Now this is good output!
         *
         * */


        // Start each thread
        for(Thread thread: threads)
            thread.start();

        // THE IMPORTANT CODE !
        /*
        * This forces the threads to complete processing and
        * terminate then allow them to continue
        *
        * So each thread will do its stuff, terminate then the next will
        * go
        *
        * Only after this loop is finished will the main thread resume doing
        * its stuff
        * */
        for(Thread thread : threads)
        {
            // thread.join();
            /*
            * To enhance the join and take care of edge cases
            * where a thread might take forever to complete
            * We can specify a max value amount of time (ms)
            * to wait for the thread to finish
            *
            * If it takes too long, this allows us to still
            * continue with the other threads calculations
            *
            * HOWEVER - if that thread is still hanging
            * after all the others are done, we need to explicitly handle that
            * (Think about how to handle this)
            * */
            thread.setDaemon(true); // Allows main to terminate
            thread.join(2000);
        }

        /*
        In the main thread, we want to print out whether the thread
        has completed calculating the factorial or not
        */
        for(int i = 0; i < inputNums.size(); i++)
        {
            FactorialThread t = threads.get(i);
            if(t.isFinished())
                System.out.println("Factorial of " + inputNums.get(i) + " is "+ t.getResult());
            else
                // You will probably handle the hanging thread here if we used join
                System.out.println("Calculation for  " + inputNums.get(i) + " is still in progress.");

        }
    }

    private static class FactorialThread extends Thread{
        private long inpNum;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inpNum)
        {
            this.inpNum=inpNum;
        }

        @Override
        public void run()
        {
            this.result = factorial(inpNum);
            this.isFinished = true;
        }

        public BigInteger factorial(long n)
        {
            BigInteger tempResult = BigInteger.ONE;
            for(long i = n; i> 0; i--)
            {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }
            return  tempResult;
        }

        public boolean isFinished()
        {
            return isFinished;
        }

        public BigInteger getResult()
        {
            return  result;
        }
    }
}

/*
* ✅ join() can make execution sequential — but not always

You’re correct if you do this:

t1.start();
t1.join();  // wait
t2.start();
t2.join();  // wait


This IS sequential.

t1 runs → finish

then t2 runs → finish

→ no concurrency benefit

So yes — in that pattern, join() destroys concurrency.

🤝 But join() is not the problem. The placement is.

Concurrency happens only if you start all threads first, then join later.

Example:

t1.start();
t2.start();

// these two run concurrently ↑↑

t1.join();
t2.join();
*
*
* */

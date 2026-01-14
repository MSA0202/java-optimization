package org.example.section3;

import java.math.BigInteger;

/*
* Here we have a exponential calculation class
* So its to replicate some task that takes a long time
* We can then interrupt if something takes too long
* But its not just going to work, to introduce interrupt below
* we need to check if the interrupt was called on the current thread
*
* */
public class LongComputationTask implements Runnable {

    private BigInteger base;
    private BigInteger power;

    public LongComputationTask(BigInteger base, BigInteger power) {
        this.base = base;
        this.power = power;
    }

    @Override
    public void run() {
        BigInteger calculate = pow(base,power);
        String calcRes = base +"^"+power+" = " + calculate;
        System.out.println(calcRes);
    }

    private BigInteger pow(BigInteger base, BigInteger power)
    {
        BigInteger result = BigInteger.ONE;
        /*
        * We have to strategically choose the location where we want to end the program
        * Typically we choose the spot where the computation is, since its the place
        * that causes the lengthy time
        * */
        for(BigInteger i = BigInteger.ZERO; i.compareTo(power) !=0 ; i=i.add(BigInteger.ONE)){
            if(Thread.currentThread().isInterrupted())
            {
                System.out.println("Prematurely interrupted the computation");
                return BigInteger.ZERO;
            }
            result = result.multiply(base);
        }
        return result;
    }

    public static void computeWithThreads()
    {
        Thread t = new Thread(new LongComputationTask(new BigInteger("2000"),new BigInteger("1000")));
        t.start();
        /*
        * Only because of the if statement introduced does this say "Prematurely interrupted the computation"
        * If there was no if check, the below does nothing!
        * */
        t.interrupt();
        /*
        We dont need explicit handling if we setDaemon to true
        * if we setDaemon to true and call the above, then we do not need the if condition
        * This daemon basically says
        * "do not prevent main from ending if it gets interrupted - TERMINATE IT WHEN INTERRUPTED!"
        * */
        //t.start();
        //t.setDaemon(true);
        //t.interrupt();
    }
}

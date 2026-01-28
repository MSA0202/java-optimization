package org.example.section6;


/*

Compiler and CPU may execute
the instructions out of order
to optimize performance and utilization,
although this is an
important feature to speed up the code.

* When multiple threads perform different tasks
* on the same set of shared objects.
* For example while thread 1 is still busy completing its method
* thread 2 is already doing something that is checking the state
* of thread 1's method ( ie as below )
* */
public class DataRaceExample {

    public static void dataRaceExampleExecute()
    {
        SharedClass sharedClass = new SharedClass();
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++)
            {
                sharedClass.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++)
            {
                sharedClass.checkForDataRace();
            }
        });

        t1.start();
        t2.start();
    }

    private static class SharedClass {

        private int x = 0;
        private int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace()
        {
            if(y>x)
            {
                System.out.println("Data Race detected! This should not be possible!");
            }
        }
    }

    /*
    How to fix the data race

    1) Fix by using sync
    keywords on methods that modify shared variables

    2) Or fix by declaring
    shared variables with the volatile keyword

     */
    public static void dataRaceExampleFixExecute()
    {
        FixedSharedClass sharedClass = new FixedSharedClass();
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++)
            {
                sharedClass.increment();
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++)
            {
                sharedClass.checkForDataRace();
            }
        });

        t1.start();
        t2.start();
    }

    private static class FixedSharedClass
    {
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace()
        {
            if(y>x)
            {
                System.out.println("Data Race detected! This should not be possible!");
            }
        }
    }
}

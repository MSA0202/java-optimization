package org.example.section2;

public class TaskOne {
    public static void creatingAThread() throws InterruptedException
    {
        // All thread related methods and stuff are part of the Thread
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //Code that will run in a new thread
                System.out.println("We are in thread Run: "+ Thread.currentThread().getName());
                System.out.println("Current Thread Priority: "+ Thread.currentThread().getPriority());
            }
        });
        /*
        Can also be written as
        Thread t = new Thread(() -> {//code that will run thread});
        */

        thread.setName("New Worker Thread");

        //Set Thread Scheduling rules
        thread.setPriority(Thread.MAX_PRIORITY);

        System.out.println("We are in Main thread: {"+ Thread.currentThread().getName() + "} before starting new thread");
        // Instructs JVM to start a new thread and pass it to the OS
        thread.start(); //When it actually prints is based on Thread Scheduling rules
        System.out.println("We are in Main thread: {"+ Thread.currentThread().getName() + "} after starting new thread");
        // During this time, the thread is not consuming any CPU
        Thread.sleep(2000);

        /*
        cd multithreading
        javac MultiThreadingOne.java
        java MultiThreadingOne.java

        OUTPUT
        We are in Main thread: {main} before starting new thread
        We are in Main thread: {main} after starting new thread
        We are in thread Run: New Worker Thread
        Current Thread Priority: 10
                <THREAD SLEEPS FOR 10 SECONDS THEN program ends>
         */
    }

    public static void misbehavingThread()
    {
        // All thread related methods and stuff are part of the Thread
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                throw new RuntimeException("Intentional Exception");
            }
        });

        /*
        - Allows us to do stuff like cleanup resources or troubleshoot if an exception occurs on a thread
        and the exception was not caught
         */
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread " + thread.getName()
                        + " the error is " + e.getMessage());
            }
        });

        thread.start();

        /*
        Output
        A critical error happened in thread Thread-0 the error is Intentional Exception
         */
    }
}

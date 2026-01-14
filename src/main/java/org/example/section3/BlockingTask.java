package org.example.section3;

public class BlockingTask implements Runnable {


    @Override
    public void run() {
        //do things
        /*
        *
        * The sleep method requires handling of InterruptedException
        * So assuming this is some hanging thread by giving it a lot of time
        * We can call thread.interrupt() to stop the threads execution
        * */
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            System.out.println("Exiting blocking thread");
        }
    }

    public void handleBlockingThreadExample()
    {
        Thread thread = new Thread(new BlockingTask());
        /*
        * This will start the thread that sleeps for 50 seconds
        * */
        thread.start();
        /*
        *
        * Interrupt the thread to exit thread execution and get out of blockage*/
        thread.interrupt();
    }
}

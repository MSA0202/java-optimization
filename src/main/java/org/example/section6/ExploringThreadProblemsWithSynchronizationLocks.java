package org.example.section6;

public class ExploringThreadProblemsWithSynchronizationLocks {

    public static void etpExecute() throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread  decrementingThread = new DecrementingThread(inventoryCounter);

        /*
        * Will now
        * produce correct result
        * */
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");


    }
    
    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter)
        {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run()
        {
            for(int i = 0; i < 10000 ; i++){
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter)
        {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run()
        {
            for(int i = 0; i < 10000 ; i++){
                inventoryCounter.decrement();
            }
        }
    }

    /*
     * Using an explicit lock object allows us to control synchronization
     * more precisely than synchronizing entire methods.
     *
     * In this structure, all access to the shared variable `items`
     * is protected by the same lock (lock1). This guarantees thread safety
     * by ensuring that only one thread at a time can:
     *   - increment items
     *   - decrement items
     *   - read items
     *
     * This means:
     * - Thread A and Thread B CANNOT execute increment() and decrement()
     *   at the same time.
     * - All operations on `items` are serialized through lock1.
     *
     * Compared to synchronizing whole methods, this approach allows us to:
     * - Synchronize only the critical section of code
     * - Keep non-critical code outside the synchronized block
     * - Reduce how long the lock is held
     *
     * This gives better control over concurrency while still preserving
     * correctness and visibility guarantees for the shared state.
     */

    /*
    * Currently, this is SERIALIZED and NOT Parallelized because we're
    * locking 1 thread at a time to do increment or decrement
    * to prevent inconsistent results
    *
    * !!!! However, as mentioned, anything in the methods outside of items++ or
    * items-- (ie out of the sync block) can benefit from concurrency !!!!
    *
    * */
    private static class InventoryCounter {
        private int items = 0;
        Object lock1 = new Object();

        /*
        * You should not use lock2 on decrement and lock1 on increment at the same time
        * because both will change the items object causing inconsistent results
        * Only create a lock for each independent method that another thread
        * can safely do without causing these issues
        * */
        Object lock2 = new Object();

        public void increment(){
            synchronized (lock1)
            {
                items++;
            }
        }

        public void decrement(){

            synchronized (lock1)
            {
                items--;
            }
        }

        public int getItems()
        {
            synchronized (lock1)
            {
                return items;
            }
        }

        public void doSomethingCompletelyDifferent()
        {
            synchronized (lock2){}
        }
    }
}

package org.example.section6;

public class ExploringThreadProblemsWithSynchronization {

    public static void etpExecute() throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread  decrementingThread = new DecrementingThread(inventoryCounter);

        /*
        *
        *
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
    * We add the synchronized specifier to the method names
    * which contain CRITICAL SECTIONS
    *
    * However, only 1 thread can execute 1 function at a time
    * E.G. if thread A executes increment(), thread B is still
    * not allowed to execute decrement()
    *
    * However we can overcome this with using a Lock Sync structure
    *
    * */
    private static class InventoryCounter {
        private int items = 0;

        public synchronized void increment(){
            items++;
        }

        public synchronized void decrement(){
            items--;
        }

        public synchronized int getItems()
        {
            return items;
        }

    }
}

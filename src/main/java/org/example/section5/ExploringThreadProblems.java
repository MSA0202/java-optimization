package org.example.section5;

public class ExploringThreadProblems {

    public static void etpExecute() throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread  decrementingThread = new DecrementingThread(inventoryCounter);

        /*
        * This is sequential execution
        * No benefit from concurrency and no problems with this
        * Output: We currently have 0 items ( which is what we expect
        * */
       /*
        incrementingThread.start();
        incrementingThread.join();
        decrementingThread.start();
        decrementingThread.join();
        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
        */

        /*
        * This takes advantage of concurrency but is problematic
        * This is problematic
        * Output is: <An inconsistent number of items each time this is run>
        * */
        /*
        *                               WHY ?
        * The InventoryCounter is a shared object. So the 'items' member is shared between
        * the 2 threads. ( Remember the quiz rules! )
        *
        * Both threads are calling items++ and items-- at the same time
        * and are not atomic operations (see notes for definition)
        *
        * items++ is not atomic because it has 3 operations
        * 1. get current value of the items
        * 2. increment current by 1
        * 3. store the result back into items
        *
        * */
        /*
        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have " + inventoryCounter.getItems() + " items");
        */

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

    private static class InventoryCounter {
        private int items = 0;

        public void increment(){
            items++;
        }

        public void decrement(){
            items--;
        }

        public int getItems()
        {
            return items;
        }

    }
}

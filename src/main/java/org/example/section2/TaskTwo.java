package org.example.section2;

/*
Easier way of creating a Thread is to extend Thread class
Better - you can implement the Runnable interface instead
so that you can extend other classes - no code changes needed !
*/
public class TaskTwo extends Thread {

    public static void newThreadCreation()
    {
        Thread newT = new TaskTwo();
        newT.start();
    }

    @Override
    public void run()
    {
        System.out.println("Task2: Hello from: "+ this.getName());
    }
}

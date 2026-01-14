package org.example.section3;

public class SectionThreeExecutor {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting Section 3 Executor");

        System.out.println("Start blocking task");
        /*new BlockingTask().handleBlockingThreadExample();*/
        System.out.println("End blocking task");

        System.out.println("Long Compute Task Start");
       /* LongComputationTask.computeWithThreads();*/
        System.out.println("Long Compute Task End");

        System.out.println("Factorial Join Task Start");
        new ExampleThreadJoin().executorThreadJoin();
        System.out.println("Factorial Join Task End");
    }
}

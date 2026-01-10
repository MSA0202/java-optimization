package org.example.section2;


public class MultiThreadingOne
{
    public static void main(String[] args) throws InterruptedException {
        /*
        Exploring Thread Creation and Thread exceptions
         */
        System.out.println("Task1 Begin");
//        TaskOne.creatingAThread();
//        TaskOne.misbehavingThread();
        System.out.println("Task1 End");
        System.out.println();

        System.out.println("Task2 Begin");
//        TaskTwo.newThreadCreation();
        System.out.println("Task2 End");
        System.out.println();

        System.out.println("Hacker/Police Case Study Task1 Start");
        new CaseStudy().playGame();
        System.out.println("Hacker/Police Case Study Task1 End");
        System.out.println();
    }


}
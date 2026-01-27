package org.example.section6;

import org.example.section5.ExploringThreadProblems;

/*
* Extends section 5
* */
public class Section6Exec {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Start Exploring Thread Solutions");
        //ExploringThreadProblemsWithSynchronizationLocks.etpExecute();
        System.out.println("End Exploring Thread Solutions");

        System.out.println("Start Exploring Thread Solutions with locks");
        //ExploringThreadProblemsWithSynchronization.etpExecute();
        System.out.println("End Exploring Thread Solutions with locks");

        System.out.println("Start Exploring Thread Solutions with locks");
        MetricsExample.metricsExecute();
        System.out.println("End Exploring Thread Solutions with locks");
    }
}

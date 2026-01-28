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
        //MetricsExample.metricsExecute();
        System.out.println("End Exploring Thread Solutions with locks");

        /*
        * Will have loads of data races
        * */
        System.out.println("Start Data Race Example");
        //DataRaceExample.dataRaceExampleExecute();
        System.out.println("End Data Race Example");

        /*
        * Will be fine - no data race
        * */
        System.out.println("Start Data Race Fix Example");
        //DataRaceExample.dataRaceExampleFixExecute();
        System.out.println("End Data Race Fix Example");
    }
}

package org.example.section4;

import java.io.IOException;

public class Section4Exec {
    public static void main(String[] args) throws IOException {

        /*
        *
        Start SingleThreaded Image Recolour
        264 (ms)
        End SingleThreaded Image Recolour
        Start MultiThreaded Image Recolour
        128 (ms) less than half the time!
        End MultiThreaded Image Recolour
        * */
        System.out.println("Start SingleThreaded Image Recolour");
//        SingleThreadedImageProcessExample.readingImageRunner();
        System.out.println("End SingleThreaded Image Recolour");

        System.out.println("Start MultiThreaded Image Recolour");
//        MultiThreadedImageProcessExample.readingImageRunner();
        System.out.println("End MultiThreaded Image Recolour");

        /*
        * After running main, below will start the server
        * Navigate to localhost:8000/search?word=talk
        * to get the server to search and return a response.
        * You should see a word count of 551 displayed on screen
        *
        * There is additional setup to automate testing performance
        * times with Apache Jmeter. Install Jmeter and rewatch video 14
        * */
        System.out.println("Start Throughput HttpServer");
        ThroughputHttpServer.httpServerExec();
        System.out.println("End Throughput HttpServer");

    }
}

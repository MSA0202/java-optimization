package org.example.section4;

import java.io.IOException;

public class Section4Exec {
    public static void main(String[] args) throws IOException {

        System.out.println("Start SingleThreaded Image Recolour");
        SingleThreadedImageProcessExample.readingImageRunner();
        System.out.println("End SingleThreaded Image Recolour");

        System.out.println("Start MultiThreaded Image Recolour");
        MultiThreadedImageProcessExample.readingImageRunner();
        System.out.println("End MultiThreaded Image Recolour");
        /*
        *
        Start SingleThreaded Image Recolour
        264 (ms)
        End SingleThreaded Image Recolour
        Start MultiThreaded Image Recolour
        128 (ms) less than half the time!
        End MultiThreaded Image Recolour
        * */
    }
}

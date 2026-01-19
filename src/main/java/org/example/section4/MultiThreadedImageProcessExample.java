package org.example.section4;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
*
* In this example we are going to convert all grey areas of a flower image
* to purple
*
* We're going to use Multithreading here.
*
* We utilise Multithreading by partitioning the image into parts, where
* a thread will recolor each part independently
*
* */
public class MultiThreadedImageProcessExample {

    public static final String SOURCE_FILE = "resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "resources/many-flowers-recolor-multi.jpg";


    public static void readingImageRunner() throws IOException {
        /* THis bi class provied represents image data
         * such as pixel colour space and dimensions. Has convenient methods */
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        long start = System.currentTimeMillis();
        recolorMultiThreaded(originalImage, resultImage, 4);
        long end = System.currentTimeMillis();

        long duration = end - start;

        File out = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", out);
        System.out.println(duration);
    }

    /*
    * This takes in an rgb values and simply extracts only the blue value
    * out of the pixel
    * We do this by using bitwise and applying the hex
    * The blue is the last byte on the right
    * */
    public static int getBlue(int rgb)
    {
        return rgb & 0x000000FF;
    }

    /* shift 8 bits right because the green is
    * second byte from the right*/
    public static int getGreen(int rgb)
    {
        return (rgb & 0x0000FF00) >> 8;
    }

    /* shift by 2 bytes*/
    public static int getRed(int rgb)
    {
        return (rgb & 0x00FF0000) >> 16;
    }

    /* if the colours are not very different to each other
    * it means that they are quite close to gray
    * remember gray, black, white colours all require RGB to be very similar
    * in values and not very biased towards either colour */
    public static boolean isShadeOfGray(int red, int green, int blue)
    {
        return Math.abs(red-green) < 30 &&
                Math.abs(red-blue) < 30 &&
                Math.abs(green-blue) < 30;
    }

    public static int createRGBFromColors(int red, int green, int blue)
    {
        int rgb = 0;
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        rgb |= 0xFF000000; //alpha

        return rgb;
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb)
    {
        image.getRaster().setDataElements(x,y,image.getColorModel().getDataElements(rgb,null));
    }

    /*
    * Method to actually recolor the pixel
    *
    * */
    public static void recolorPixel(BufferedImage orig, BufferedImage res, int pixelX, int pixelY)
    {
        int rgb = orig.getRGB(pixelX,pixelY);
        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed;
        int newGreen;
        int newBlue;

        /*
        * Remember we're recoloring grey to purple
        * */
        if(isShadeOfGray(red, green, blue)) {
            // We're trying to create purple here
            // You can trial and error these to get it right
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green-80);
            newBlue = Math.max(0, blue-20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
        setRGB(res, pixelX, pixelY, newRGB);
    }


    public static void recolorImage(BufferedImage orig, BufferedImage res, int leftCorner,
                                    int topCorner, int width, int height)
    {
        for(int x = leftCorner; x < leftCorner + width && x < orig.getWidth(); x++)
        {
            for(int y = topCorner; y < topCorner + height && y < orig.getHeight(); y++)
            {
                recolorPixel(orig, res, x, y);
            }
        }
    }


    public static void recolorMultiThreaded(BufferedImage orig, BufferedImage res, int numberOfThreads)
    {
        List<Thread> threads = new ArrayList<>();

        int width = orig.getWidth();
        int height = orig.getHeight() / numberOfThreads;

        for(int i = 0; i < numberOfThreads; i++)
        {
            final int threadMultiplier = i;
            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;
                recolorImage(orig,res,leftCorner,topCorner, width, height);
            });
            threads.add(thread);
        }

        for(Thread t : threads)
        {
            t.start();
        }

        for(Thread t : threads)
        {
            try {
                t.join();
            } catch (InterruptedException e) {

            }
        }
    }


}

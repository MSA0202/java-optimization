package org.example.section6;

import java.util.Random;

public class MetricsExample {

    public static void metricsExecute() {
        Metrics m = new Metrics();

        BusinessLogic t1 = new BusinessLogic(m);
        BusinessLogic t2 = new BusinessLogic(m);

        MetricsPrinter p = new MetricsPrinter(m);

        t1.start();
        t2.start();
        p.start();
        /*
            join() is used when you need to wait for a thread to finish.
            In this code, all threads run in infinite loops (while(true)), so they never finish.
            Calling join() would block forever and serve no purpose.
            This program models a live, continuous metrics system, not a finite batch job.
            Therefore, join() is intentionally not used.

            Remember that join is used when threads MUST be COORDINATED

            Clearly here, they dont need to be!
        * */
    }

    public static class Metrics
    {
        private long count = 0;
        private volatile double average = 0.0;

        /*
         * Multiple threads will share the MetricsExample object,
         * hence count and average will be shared and open to be
         * modified by multiple threads
         * so we need to protect this method using
         * the sync keyword
         * */
        public synchronized void addSample(long sample) {
            double currentSum = average * count;
            count++;
            average = (currentSum + sample) / count;
        }

        /*
         * All getter methods are sync ( see notes !)
         * */
        public double getAverage() {
            return average;
        }

    }

    public static class BusinessLogic extends Thread
    {
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics)
        {
            this.metrics = metrics;
        }

        @Override
        public void run()
        {
            while(true) {

                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long end = System.currentTimeMillis();

                metrics.addSample(end - start);
            }

        }
    }

    public static class MetricsPrinter extends Thread
    {
        private Metrics metrics;
        private Random random = new Random();

        public MetricsPrinter(Metrics metrics)
        {
            this.metrics = metrics;
        }

        @Override
        public void run()
        {
            while(true) {

                try {
                    Thread.sleep(random.nextInt(100));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double currentAverage = metrics.getAverage();
                System.out.println("Current Average: "+ currentAverage);
            }

        }
    }


}

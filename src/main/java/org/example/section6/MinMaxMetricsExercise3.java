package org.example.section6;


/*
* Not executable as we dont know the Test Code
* Our solution is good, see below for better solution
* */
public class MinMaxMetricsExercise3 {

    // Add all necessary member variables
    volatile long min;
    volatile long max;

    /**
     * Initializes all member variables
     */
    public MinMaxMetricsExercise3() {
        // Add code here
        this.min = 0;
        this.max = 0;
    }

    /**
     * Adds a new sample to our metrics.
     */
    public synchronized void addSample(long newSample) {
        // Add code here
        this.min = Math.min(newSample, min);
        this.max = Math.max(newSample, max);
    }

    /**
     * Returns the smallest sample we've seen so far.
     */
    public long getMin() {
        // Add code here
        return this.min;
    }

    /**
     * Returns the biggest sample we've seen so far.
     */
    public long getMax() {
        // Add code here
        return this.max;
    }


}
/*
* Min - Max Metrics
In this exercise, we are going to implement a class called MinMaxMetrics .

A single instance of this class will be passed to multiple threads in our application.

MinMaxMetrics is an analytics class used to keep track of the minimum and the maximum of a particular business or performance metric in our application.

Example:

A stock trading application that keeps track of the minimum and maximum price of the stock daily.



The class will have 3 methods:

addSample(..) - Takes a new sample.

getMin() - Returns the sample with the minimum value we have seen so far.

getMax() - Returns the sample with the maximum value we have seen so far.



Notes:

- Each method can be called concurrently by any given number of threads, so the class needs to be thread-safe.

- In addition, this class is used for analytics, so it needs to be as performant as possible, as we don't want it to slow down our business logic threads too much.

- Threads that call getMin() or getMax() are interested in only one of the values and are never interested in both the min and the max at the same time


Please implement MinMaxMetrics below:



Important Note:

Only the logic of the class is unit tested, and it is impossible to test a class's thread safety.

The solution to the exercise is provided in the next lecture.
*
* */

/*
*
* Better solution
*
 */
/*public class MinMaxMetrics {

    private volatile long minValue;
    private volatile long maxValue;

    *//**
     * Initializes all member variables
     *//*
    public MinMaxMetrics() {
        this.maxValue = Long.MIN_VALUE;
        this.minValue = Long.MAX_VALUE;
    }

    *//**
     * Adds a new sample to our metrics.
     *//*
    public void addSample(long newSample) {
        synchronized (this) {
            this.minValue = Math.min(newSample, this.minValue);
            this.maxValue = Math.max(newSample, this.maxValue);
        }
    }

    *//**
     * Returns the smallest sample we've seen so far.
     *//*
    public long getMin() {
        return this.minValue;
    }

    *//**
     * Returns the biggest sample we've seen so far.
     *//*
    public long getMax() {
        return this.maxValue;
    }
}*/

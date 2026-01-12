package org.example.section2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CaseStudy {

    public static final int MAX_PASSWORD = 9999;
    /*
    - We are going to have some Vault
    - Then we gonna allow hackers ( each hacker represented by a hackerthread try to crack the vault
    to steal our money through brute forcing the code
    - We then have a police thread which will count down 10 seconds before arresting the thieves
    if they did not crack the code
    - We will additionally show the progress of how far we are
     */

    private static class Vault {
        private int password;
        public Vault(int password)
        {
            this.password=password;
        }

        public int getPassword() {
            return password;
        }

        public void setPassword(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess)
        {
            // Slow down password checking for hackers to delay them
            try{
                Thread.sleep(5);
            }
            catch (InterruptedException e)
            {
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault)
        {
            this.vault = vault;
            // set thread name to class name
            this.setName(this.getClass().getName());
            // thread priority
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start()
        {
            super.start();
            System.out.println("Starting thread: " + this.getName());
        }
    }

    /*
    * CREATING 2 HACKER THREAD TYPE CLASSES
    *
    *
    * */
    /*
    * This will guess the password by iterating through password in ascending order
    *
    * */
    private static class AscendingHackerThread extends HackerThread {
        public AscendingHackerThread(Vault vault)
        {
            super(vault);
        }

        @Override
        public void run()
        {
            for (int guess = 0; guess < MAX_PASSWORD; guess++)
            {
                // System.out.println(this.getName() + " is guessing password "+guess);
                if(vault.isCorrectPassword(guess))
                {
                    System.out.println(this.getName() + " guessed the password "+guess);
                    System.exit(0); //exit after hacking
                }
            }
        }
    }

    /*
     * This will guess the password by iterating through password in desc order
     *
     * */
    private static class DescendingHackerThread extends HackerThread {
        public DescendingHackerThread(Vault vault)
        {
            super(vault);
        }

        @Override
        public void run()
        {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--)
            {
                // System.out.println(this.getName() + " is guessing password "+guess);
                if(vault.isCorrectPassword(guess))
                {
                    System.out.println(this.getName() + " guessed the password "+guess);
                    System.exit(0); //exit after hacking
                }
            }
        }
    }

    /*
     * This will guess the password by iterating through password in desc order
     *
     * */
    private static class PoliceThread extends Thread {

        /*
        * Police will count 10 seconds with a delay of 1000 ms between each count
        * If the hackers havent cracked the password by then - game over
        * */
        @Override
        public void run()
        {
            for (int i = 10; i >= 0; i--)
            {
                try{
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                }
                System.out.println("Time remaining until police: " +i);
            }
            System.out.println("Game over. Hackers caught!");
            System.exit(0);
        }
    }

    public void playGame()
    {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();
        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for(Thread thread: threads)
        {
            thread.start();// will call the run method for each thread
        }

    }


}

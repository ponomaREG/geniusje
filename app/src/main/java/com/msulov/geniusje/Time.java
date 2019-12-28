package com.msulov.geniusje;

public class Time implements Runnable {

    public float time = 0;
    private boolean hasContinue = true;

    @Override
    public void run() {
        while (hasContinue) {
            try {
                Thread.sleep(100);
                time += 0.1;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopTime() {
        hasContinue = false;
    }
}

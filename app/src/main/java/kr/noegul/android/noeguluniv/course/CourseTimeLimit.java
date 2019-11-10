package kr.noegul.android.noeguluniv.course;

import android.app.Activity;
import android.widget.ProgressBar;

public class CourseTimeLimit {
    private static final float LIMIT_TIME = 60;
    private final Activity activity;
    private final ProgressBar progressBar;
    private Runnable finishHandler;
    private Thread timerThread;
    private float timeRemaining;
    private boolean started;

    public CourseTimeLimit(Activity activity, ProgressBar progressBar) {
        this.activity = activity;
        this.progressBar = progressBar;
        this.timeRemaining = 0;
        this.started = false;
    }

    public void start() {
        if (hasStarted())
            throw new IllegalStateException("Time limit already started");

        this.timeRemaining = LIMIT_TIME;
        this.timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeRemaining > 0) {
                    timeRemaining -= 0.01;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress((int) (timeRemaining / LIMIT_TIME * 1000));
                        }
                    });

                    try {
                        Thread.sleep(10L);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
                activity.runOnUiThread(finishHandler);
            }
        });
        timerThread.start();
        started = true;
    }

    public void stop() {
        if (!hasStarted())
            throw new IllegalStateException("Time limit didn't start yet");

        timerThread.interrupt();
        started = false;
    }

    public boolean hasStarted() {
        return started;
    }

    public Runnable getFinishHandler() {
        return finishHandler;
    }

    public void setFinishHandler(Runnable finishHandler) {
        this.finishHandler = finishHandler;
    }
}

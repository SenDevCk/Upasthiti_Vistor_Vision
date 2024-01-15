package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

import Upasthiti.vistor_vision.in.eAttendance.interfaces.LogOutLisiner;


public class SessionExpire extends Application {
    private LogOutLisiner listener;
    private Timer timer;

    public void startUserSession() {
         cancleTimer();
         timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listener.onSessionLogOut();

            }
        },900000);
    }

    private void cancleTimer() {
        if (timer !=null) timer.cancel();
    }


    public void registerSessionListener(LogOutLisiner listener) {
        this.listener = listener; 
    }

    public void onUserIntracted() {
        startUserSession();
    }
}

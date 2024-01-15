package Upasthiti.vistor_vision.in.eAttendance.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import Upasthiti.vistor_vision.in.eAttendance.interfaces.LogOutLisiner;

public class LogOut extends AppCompatActivity implements LogOutLisiner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ( (SessionExpire) getApplication()).registerSessionListener(this);
        ( (SessionExpire) getApplication()).startUserSession();

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        ( (SessionExpire) getApplication()).onUserIntracted();
    }

    @Override
    public void onSessionLogOut() {
        finish();

        startActivity(new Intent(this,LoginActivity.class));
    }
}

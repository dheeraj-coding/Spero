package com.vitanova.dheeraj.spero;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by dheeraj on 20/1/18.
 */

public class UpdateService extends Service {

    BroadcastReceiver mReceiver;


    public UpdateService() {
        super();
        mReceiver=new Receiver();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        boolean screenOn = intent.getBooleanExtra("screen_state", false);
        if (!screenOn) {

        } else {

            Intent dialogIntent = new Intent(this, MainActivity.class);
            dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(dialogIntent);

        }
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}

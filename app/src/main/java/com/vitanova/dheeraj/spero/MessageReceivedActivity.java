package com.vitanova.dheeraj.spero;

import android.content.Context;
import android.content.Intent;
import android.gesture.Gesture;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MessageReceivedActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    GestureDetectorCompat mDetector;
    Vibrator v;
    public MessageReceivedActivity() {
        super();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_received);

        mDetector=new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        v= (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);



        Intent i = getIntent();
        String message=i.getStringExtra("message");
        MorseConverter converter=new MorseConverter();
        String morse=converter.stringToMorse(message);
        TextView tv=(TextView)findViewById(R.id.contentTV);
        tv.setText(message);
        long[] pattern =VibrationPattern.generate(morse);

        v.vibrate(pattern,-1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return true;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return true;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        return true;
    }
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        v.cancel();
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        this.finish();
        return true;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {

        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}

package com.vitanova.dheeraj.spero;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BookReaderActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    GestureDetectorCompat mDetector;
    String content;
    long pattern[];
    MorseConverter converter;
    Vibrator v;
    String line;
    TextView tv;

    public BookReaderActivity() {
        super();
        this.line="";
        converter=new MorseConverter();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);

        mDetector =new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);



        tv=(TextView)findViewById(R.id.morseTV);
        FileOperations operator =new FileOperations();
        operator.flag=1;
        int previous=0;

        content=operator.read("book");
        content=content+"-end";
        Log.d("book",content);

        if(content!=null ){
            if(!content.equals("-end")){
                if(content.contains("/")){
                    Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
                    line=content.substring(0,content.indexOf('/'));
                    content=content.substring(content.indexOf('/')+1);
                    String morse=converter.stringToMorse(line);
                    tv.setText(line);
                    pattern=VibrationPattern.generate(morse);
                    v.vibrate(pattern,-1);
                }
            }

        }
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
        if(!content.equals("-end")){

            line=content.substring(0,content.indexOf('/'));
            line=line.trim();
            content=content.substring(content.indexOf('/')+1);
            Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
            String morse=converter.stringToMorse(line);
            pattern = VibrationPattern.generate(morse);
            tv.setText(line);
            this.v.vibrate(pattern,-1);
        }else{
            line="end";
            FileOperations operator=new FileOperations();
            content=content.replace('/',' ');
            content=content.substring(0,content.length()-4);
            content=content.trim();
            operator.write("book",content);
            String morse=converter.stringToMorse(line);
            pattern=VibrationPattern.generate(morse);
            tv.setText(line);
            this.v.vibrate(pattern,-1);
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
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
        FileOperations operator=new FileOperations();
        content=content.replace('/',' ');
        content=content.substring(0,content.length()-4);
        content=content.trim();
        operator.write("book",content);
        Intent i =new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
        finish();
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


















}

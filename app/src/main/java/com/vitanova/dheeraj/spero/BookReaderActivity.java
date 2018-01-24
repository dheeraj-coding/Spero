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
import android.view.WindowManager;
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
    String theBook;
    public BookReaderActivity() {
        super();
        this.line="";
        converter=new MorseConverter();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_reader);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        theBook="When forty winters shall besiege thy brow,\n" +
                "And dig deep trenches in thy beauty's field,\n" +
                "Thy youth's proud livery so gazed on now,\n" +
                "Will be a totter'd weed of small worth held:\n" +
                "Then being asked, where all thy beauty lies,\n" +
                "Where all the treasure of thy lusty days;\n" +
                "To say, within thine own deep sunken eyes,\n" +
                "Were an all-eating shame, and thriftless praise.\n" +
                "How much more praise deserv'd thy beauty's use,\n" +
                "If thou couldst answer 'This fair child of mine\n" +
                "Shall sum my count, and make my old excuse,'\n" +
                "Proving his beauty by succession thine!\n" +
                "   This were to be new made when thou art old,\n" +
                "   And see thy blood warm when thou feel'st it cold.";
        mDetector =new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);



        tv=(TextView)findViewById(R.id.morseTV);
        FileOperations operator =new FileOperations();
        operator.flag=1;
        int previous=0;

        content=operator.read("book");
        if(content==null){
            operator.write("book",theBook);
        }
        content=operator.read("book");

        content=content+"-end";


        if(content!=null ){
            if(!content.equals("-end")){
                if(content.contains("/")){
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
            Log.d("book",content);
            line=content.substring(0,content.indexOf('/'));
            line=line.trim();
            content=content.substring(content.indexOf('/')+1);
            String morse=converter.stringToMorse(line);
            pattern = VibrationPattern.generate(morse);
            tv.setText(line);
            this.v.vibrate(pattern,-1);
        }else{
            line="end";
            FileOperations operator=new FileOperations();
            content=content.replace('/','\n');
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
        content=content.replace('/','\n');
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

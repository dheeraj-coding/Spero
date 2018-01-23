package com.vitanova.dheeraj.spero;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =1 ;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =2 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS =3 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =4 ;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE =5 ;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE =6 ;
    String task;
    private GestureDetectorCompat mDetector;
    String morse;
    MorseConverter converter;
    FileOperations operator;
    BroadcastReceiver mReceiver;
    Vibrator v;
    int flag;
    public MainActivity() {
        super();
        flag=0;

        this.task="";
        this.morse="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver=new Receiver();
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);

        }

        mDetector=new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        v=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);



        Toolbar toolbar =(Toolbar)findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(toolbar);

        converter=new MorseConverter();
        operator=new FileOperations();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver, filter);



        startService(new Intent(this, UpdateService.class));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        if(action==MotionEvent.ACTION_POINTER_DOWN){

            if(task.equals("m")){
                String content=converter.morseToString(morse);
                Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
                v.vibrate(500);
                String possibleNumber=content.substring(0,content.indexOf(' '));
                long number;
                try{

                    number = Long.parseLong(possibleNumber);
                    String num=String.valueOf(number);
                    content=content.substring(content.indexOf(' '));
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(num,null,content,null,null);
                }catch(NumberFormatException e){
                    String line;
                    line = operator.read("details");
                    String name=line.substring(0,line.indexOf(','));
                    String num=line.substring(line.indexOf(',')+1);
                    SmsManager sms=SmsManager.getDefault();
                    sms.sendTextMessage(num,null,content,null,null);
                }
                morse="";
                task="";
                flag=0;
            }else if(task.equals("p")){
                if(morse.equals("")){
                    String line;
                    line = operator.read("details");
                    String name=line.substring(0,line.indexOf(','));
                    String number=line.substring(line.indexOf(',')+1);
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    startActivity(callIntent);
                }else{
                    String number=converter.morseToString(morse);
                    Intent callIntent=new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    startActivity(callIntent);
                }
                flag=0;
                task="";
                morse="";

            }else if(task.equals("r")){
                Intent bookStart=new Intent(getApplicationContext(),BookReaderActivity.class);
                startActivity(bookStart);
                morse="";
                flag=0;
                task="";
                finish();

            }else{
                long pattern[]=VibrationPattern.generate(converter.stringToMorse("choose"));
                v.vibrate(pattern,-1);
            }
        }else{
            this.mDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(flag==0){
            flag=1;
            task="m";
            this.v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Message",Toast.LENGTH_SHORT).show();
        }else if(flag==1 && morse.equals("")){
            flag=0;
            task="";
            this.v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Message Cancel",Toast.LENGTH_SHORT).show();

        }else{
            if(!morse.equals("")){
                morse=morse.substring(0,morse.length()-1);
            }
            Toast.makeText(getApplicationContext(),morse,Toast.LENGTH_SHORT).show();
        }

        return true;
    }
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        if(flag==0){
            flag=2;
            task="r";
            this.v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Read",Toast.LENGTH_SHORT).show();
        }else if(flag==2 && morse.equals("")){
            flag=0;
            task="";
            this.v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Read Cancel",Toast.LENGTH_SHORT).show();

        }else{
            morse=morse+"-";
            Toast.makeText(getApplicationContext(),"-",Toast.LENGTH_SHORT).show();
        }

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
        if(flag==0){
            flag=3;
            task="p";
            v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Call",Toast.LENGTH_SHORT).show();
        }else if(flag==3 && morse.equals("")){
            flag=0;
            task="";
            this.v.vibrate(100);
            Toast.makeText(getApplicationContext(),"Call Cancel",Toast.LENGTH_SHORT).show();

        }else{
            morse=morse+".";
            Toast.makeText(getApplicationContext(),".",Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {

        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        morse=morse+" ";
        Toast.makeText(getApplicationContext(),"space",Toast.LENGTH_SHORT).show();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_settings:
                settingsOnClick(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    public void settingsOnClick(MenuItem item) {
        Intent i=new Intent(getApplicationContext(),SettingsActivity.class);
        startActivity(i);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},
                            MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},
                            MY_PERMISSIONS_REQUEST_SEND_SMS);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_SMS},
                            MY_PERMISSIONS_REQUEST_READ_SMS);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}

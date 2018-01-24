package com.vitanova.dheeraj.spero;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    FileOperations operator;
    EditText nameEt;
    EditText numberEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar=(Toolbar)findViewById(R.id.settingsActivityToolbar);
        setSupportActionBar(toolbar);

        TextView tv=(TextView)findViewById(R.id.user_manual_tv);
        tv.setMovementMethod(new ScrollingMovementMethod());

        operator=new FileOperations();
        Button updateBtn=(Button)findViewById(R.id.updateBtn);
        nameEt=(EditText)findViewById(R.id.nameET);
        numberEt=(EditText)findViewById(R.id.numberET);

        if(operator.read("details")!=null){
            String value=operator.read("details");
            String name=value.substring(0,value.indexOf(','));
            String number=value.substring(value.indexOf(',')+1);
            nameEt.setText(name);
            numberEt.setText(number);
        }


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=String.valueOf(nameEt.getText())+",";
                String number=String.valueOf(numberEt.getText());
                String fileValue=name+number;
                operator.write("details",fileValue);
            }
        });

    }
}

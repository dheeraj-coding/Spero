package com.vitanova.dheeraj.spero;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by dheeraj on 20/1/18.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras=intent.getExtras();
        if(intentExtras!=null){
            Object[] sms=(Object[])intentExtras.get("pdus");
            String smsStr="";
            for(int i=0;i<sms.length;i++){
                SmsMessage smsMessage=SmsMessage.createFromPdu((byte[])sms[i]);
                String message=String.valueOf(smsMessage.getMessageBody());
                String number= String.valueOf(smsMessage.getOriginatingAddress());

                smsStr="From "+number+" Message "+message;
            }
            Intent inte = new Intent(context,MessageReceivedActivity.class);
            inte.putExtra("message",smsStr);
            context.startActivity(inte);
        }
    }
}

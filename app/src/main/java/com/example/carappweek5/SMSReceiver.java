package com.example.carappweek5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
    public static final String SMS_FILTER = "SMS_FILTER";
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";

    /*
     * This method 'onReceive' will be invoked with each new incoming SMS
     * */
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Use the Telephony class to extract the incoming messages from the intent
         * */
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String message = currentMessage.getDisplayMessageBody();

            /*
             * Now, for each new message, send a broadcast contains the new message to MainActivity
             * The MainActivity has to tokenize the new message and update the UI
             * */
//            Android Intent is the message that is passed between components such as activities,
//            content providers, broadcast receivers, services etc.
//            It is generally used with startActivity() method to invoke activity, broadcast receivers etc.

            Intent msgIntent = new Intent();
            // on receive will run when your broadcast receive is on
            // setAction
            msgIntent.setAction(SMS_FILTER);
            // store values by its key value pairs
            msgIntent.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(msgIntent);
//            setAction sets the inital operation for the intent
//            and the putExtra basically adds extended data to the intent by using the key/value pairs

        }
    }
}
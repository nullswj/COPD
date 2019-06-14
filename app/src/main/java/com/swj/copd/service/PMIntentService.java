package com.swj.copd.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.swj.copd.io.CopdClient;
import com.swj.copd.io.ProtocolMsg;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PMIntentService extends IntentService {

    private static String TAG = "PMIntentService";

    public PMIntentService() {
        super("PMIntentService");
    }

    public static final String PM25_RECEIVE_MESSAGE = "com.swj.copd.service.action.PM25_RECEIVE_MESSAGE";

    private static CopdClient pm25client;


    public static void startPm25ReceiveMessage(Context context){
        Intent intent = new Intent(context, PMIntentService.class);
        intent.setAction(PM25_RECEIVE_MESSAGE);
        context.startService(intent);
        Log.e(TAG, "PM服务启动" );
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pm25client = new CopdClient("47.106.151.249", 8082,2);
                    pm25client.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (PM25_RECEIVE_MESSAGE.equals(action)) {
                try {
                    while (true) {
                        Thread.sleep(10000);
                        Log.e(TAG, "进入PM服务");
                        ProtocolMsg protocolMsg = null;
                        protocolMsg = pm25client.getHandler().getMessage();
                        if(protocolMsg != null) {
                            String msgBody = protocolMsg.getBody();
                            handleAction(msgBody,PM25_RECEIVE_MESSAGE);
                            Log.e(TAG, "PM"+msgBody);
                        }
                        else {
                            Log.e(TAG, "PM：protocolMsg为空" );
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleAction(String msg,String Action) {
        Intent broadcast = new Intent();
        broadcast.setAction(Action);
        broadcast.putExtra("msg",msg);
        sendBroadcast(broadcast);
    }

}

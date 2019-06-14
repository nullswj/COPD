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
public class TemIntentService extends IntentService {

    private static String TAG = "TemIntentService";

    public static final String TIWEN_RECEIVE_MESSAGE = "com.swj.copd.service.action.TIWEN_RECEIVE_MESSAGE";

    private static CopdClient tiwenclient;

    public TemIntentService() {
        super("TemIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startTiwenReceiveMessage(Context context){
        Intent intent = new Intent(context, TemIntentService.class);
        intent.setAction(TIWEN_RECEIVE_MESSAGE);
        context.startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tiwenclient = new CopdClient("47.106.151.249", 8082,1);
                    tiwenclient.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Log.e(TAG, "进入服务");
            final String action = intent.getAction();
            Log.e(TAG, "服务号 = "+action );

            if(TIWEN_RECEIVE_MESSAGE.equals(action)) {
                try {
                    while (true) {
                        Thread.sleep(10000);
                        Log.e(TAG, "进入体温服务");
                        ProtocolMsg protocolMsg = null;
                        protocolMsg = tiwenclient.getHandler().getMessage();
                        if(protocolMsg != null)
                        {
                            String msgBody = protocolMsg.getBody();
                            handleAction(msgBody,TIWEN_RECEIVE_MESSAGE);
                            Log.e(TAG, "体温"+msgBody);
                        }
                        else
                        {
                            Log.e(TAG, "体温：protocolMsg为空" );
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

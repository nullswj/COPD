package com.swj.copd.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.swj.copd.io.CopdClient;
import com.swj.copd.io.CopdClientHandler;
import com.swj.copd.io.ProtocolMsg;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DataIntentService extends IntentService {

    private static String TAG = "DataIntentService";

    public static final String XUEYANG_RECEIVE_MESSAGE = "com.swj.copd.service.action.XUEYANG_RECEIVE_MESSAGE";
    public static final String TIWEN_RECEIVE_MESSAGE = "com.swj.copd.service.action.TIWEN_RECEIVE_MESSAGE";
    public static final String PM25_RECEIVE_MESSAGE = "com.swj.copd.service.action.PM25_RECEIVE_MESSAGE";

    private static CopdClient xueyangclient;
    private static CopdClient tiwenclient;
    private static CopdClient pm25client;

    public DataIntentService() {
        super("DataIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startXueyangReceiveMessage(Context context){
        Intent intent = new Intent(context, DataIntentService.class);
        intent.setAction(XUEYANG_RECEIVE_MESSAGE);
        context.startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    xueyangclient = new CopdClient("47.106.151.249", 8082,0);
                    Log.e(TAG, "xueyangclient创建成功");
                    xueyangclient.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void startTiwenReceiveMessage(Context context){
        Intent intent = new Intent(context, DataIntentService.class);
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

    public static void startPm25ReceiveMessage(Context context){
        Intent intent = new Intent(context, DataIntentService.class);
        intent.setAction(PM25_RECEIVE_MESSAGE);
        context.startService(intent);

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
        Log.e(TAG, "进入服务1");
        if (intent != null)
        {
            Log.e(TAG, "进入服务2");
            final String action = intent.getAction();
            Log.e(TAG, "Action = "+action );
            if (XUEYANG_RECEIVE_MESSAGE.equals(action)) {
                try {
                    while (true) {
                        Thread.sleep(2000);
                        Log.e(TAG, "进入服务3");
                        ProtocolMsg protocolMsg = null;
                        CopdClientHandler handler = xueyangclient.getHandler();
                        protocolMsg = handler.getMessage();
                        if(protocolMsg != null) {
                            String msgBody = protocolMsg.getBody();
                            handleAction(msgBody,XUEYANG_RECEIVE_MESSAGE);
                            Log.e(TAG, msgBody);
                        }
                        else
                            Log.e(TAG, "protocolMsg为空" );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(TIWEN_RECEIVE_MESSAGE.equals(action))
            {
                try
                {
                    while (true)
                    {
                        Log.e(TAG, "进入服务4");
                        ProtocolMsg protocolMsg = null;
                        protocolMsg = tiwenclient.getHandler().getMessage();
                        if(protocolMsg != null)
                        {
                            String msgBody = protocolMsg.getBody();
                            handleAction(msgBody,TIWEN_RECEIVE_MESSAGE);
                            Log.e(TAG, msgBody);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(PM25_RECEIVE_MESSAGE.equals(action))
            {
                try
                {
                    while (true)
                    {
                        Log.e(TAG, "进入服务5");
                        ProtocolMsg protocolMsg = null;
                        protocolMsg = pm25client.getHandler().getMessage();
                        if(protocolMsg != null)
                        {
                            String msgBody = protocolMsg.getBody();
                            handleAction(msgBody,PM25_RECEIVE_MESSAGE);
                            Log.e(TAG, msgBody);
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

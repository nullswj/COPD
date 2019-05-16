package com.swj.copd.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DataIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_RECEIVE_MESSAGE = "com.swj.copd.service.action.RECEIVE_MESSAGE";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.swj.copd.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.swj.copd.service.extra.PARAM2";

    private String time = "12.19";
    private String value = "18";

    public DataIntentService() {
        super("DataIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionReceiveMessage(Context context, String param1) {
        Intent intent = new Intent(context, DataIntentService.class);
        intent.setAction(ACTION_RECEIVE_MESSAGE);
        intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RECEIVE_MESSAGE.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                try {
                    while (true)
                    {
                        Thread.sleep(10000);
                        handleActionFoo();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo() {
        Intent broadcast = new Intent();
        broadcast.setAction(ACTION_RECEIVE_MESSAGE);
        broadcast.putExtra("time",time);
        broadcast.putExtra("value",value);
        sendBroadcast(broadcast);
    }
}

package edu.uw.servicedemo;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

/**
 * Created by iguest on 2/16/16.
 */
public class CountingService extends IntentService {

    private static final String TAG = "**IntentService**";

    //simple constructor for counting service; for debugging purpose
    public CountingService(){
        super("CountingService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(TAG, "Service created");
    }

    //when the service has no more commands to do, it shuts itself down
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, "Service destroyed D:");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Received intent");

        //do not recreate the service, unless there are pending intents to deliver
        //return Service.START_NOT_STICKY;

        ////recreate the service and call onStartCommand(), but do not redeliver the last intent
        //return Service.START_STICKY;

        //recreate the service and call onStartCommand() with the last intent that was delivered to the service
        //return Service.START_REDELIVER_INTENT;


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v(TAG, "Handling intent");

        //wake for 5 seconds and count; should take about 1 min to complete the counting
        for(int i = 0; i < 10; i++){
            Log.v(TAG, ""+i);

            //if(i == 3) {
            //    stopSelf(); //stop the service when i reaches 3
            //}

            try {
                //tells the current process to sleep
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                //interrupt the sleeping thread
                Thread.currentThread().interrupt();
            }
        }
    }
}

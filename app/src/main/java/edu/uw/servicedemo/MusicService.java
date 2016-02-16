package edu.uw.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ServiceConfigurationError;

/**
 * Created by iguest on 2/16/16.
 */
public class MusicService extends Service implements MediaPlayer.OnCompletionListener {

    private static final String TAG = "**Music**";

    private MediaPlayer mediaPlayer;

    private int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "Music service created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String songName = "Verdi"; //should not be hard-coded in real case
        //loading up the media player in the background
        mediaPlayer = MediaPlayer.create(this, R.raw.verdi_la_traviata_brindisi_mit);
        mediaPlayer.setOnCompletionListener(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Notification notification = new NotificationCompat.Builder(this)
                .addAction(android.R.drawable.ic_media_play, "Music Player", pendingIntent)
                .setContentText("Now playing: " + songName)
                .setOngoing(true)
                .build();
        //startForeground(1, notification);
        startForeground(NOTIFICATION_ID, notification);


        mediaPlayer.start();

        Log.v(TAG, "Playing music :D");

        super.onStartCommand(intent, flags, startId);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);

        Log.v(TAG, "Stopping music D:");

        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release(); //release the resource
            mediaPlayer = null;
        }
    }

    //when the media player finishes playing the music, kill itself
    @Override
    public void onCompletion(MediaPlayer mp) {
        stopSelf();
    }

    public String sayHello(){
        return "Hello World";
    }

    //a connection between the service and the object
    private final IBinder mLocalBinder = new LocalBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    //make a local binder class
    class LocalBinder extends Binder {
        //return the service that we are running in
        public MusicService getService() {
            return MusicService.this;
        }
    }
}

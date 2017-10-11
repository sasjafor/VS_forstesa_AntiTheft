package ch.ethz.inf.vs.a1.forstesa.antitheft;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

public class AntiTheftService extends Service implements AlarmCallback {

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(this.getString(R.string.notificationTitle))
                .setContentText(this.getString(R.string.notificationText));

        Intent intent = new Intent(this, AntiTheftService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(noteID, notification);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mp = MediaPlayer.create(this, R.raw.sound);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int sens = Integer.parseInt(sharedPreferences.getString("sensitivity", "10"));
        int detector = Integer.parseInt(sharedPreferences.getString("detector", "0"));

        detect = new SpikeMovementDetector(this, sens);
        detect.setDetector(detector);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(detect, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        return START_NOT_STICKY;
    }

    public void onDelayStarted() {
        int delay;
        String numberString = sharedPreferences.getString("delay", "4000");
        if (numberString.equals("")) {
            delay = 4000;
        } else {
            delay = Integer.parseInt(numberString);
        }

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            // was interrupted
        }

        if (mp != null) {
            mp.start();
        }
    }

    @Override
    public void onDestroy() {
        if (mp != null) {
            mp.stop();
            mp.release();
        }

        sensorManager.unregisterListener(detect);
        notificationManager.cancel(noteID);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private int noteID = 1;
    private NotificationManager notificationManager;
    private SharedPreferences sharedPreferences;
    private SensorManager sensorManager;
    private SpikeMovementDetector detect;
    private MediaPlayer mp;
}

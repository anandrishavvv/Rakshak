
package com.developer.Safety;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.SmsManager;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.github.tbouron.shakedetector.library.ShakeDetector;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class ServiceMine extends Service {

    static boolean isRunning = false;
    SharedPreferences sharedPreferences;
    static MediaPlayer mediaPlayer;
    private int shakecounter = 0;


    private static final int SHAKE_THRESHOLD = 3;

    FusedLocationProviderClient fusedLocationClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    SmsManager manager = SmsManager.getDefault();
    //    WindowManager windowManager;
    String myLocation;
    View view;

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {

        super.onCreate();

        mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.siren);
        mediaPlayer.setLooping(true);




        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            // Logic to handle location object
                            location.getAltitude();
                            location.getLongitude();
                            myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                        } else {
                            myLocation = "Unable to Find Location :(";
                        }
                    }
                });


//       windowManager= (WindowManager) getSystemService(WINDOW_SERVICE);
//
//        WindowManager.LayoutParams params= null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            params = new WindowManager.LayoutParams(
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.WRAP_CONTENT,
//                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//                    PixelFormat.TRANSLUCENT);
//        }
//
//        params.gravity = Gravity.START;
//        params.x = 0;
//        params.y = 100;
//
//        LayoutInflater inflater = (LayoutInflater)   getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.floating_menu, null);
//        Button panic = view.findViewById(R.id.panicService);
//        panic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mediaPlayer.start();
//
//
//                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
//                Set<String> oldNumbers = sharedPreferences.getStringSet("enumbers", new HashSet<>());
//                if(!oldNumbers.isEmpty()){
//                    for(String ENUM : oldNumbers)
//                        manager.sendTextMessage(ENUM,null,"Im in Trouble!\nSending My Location :\n"+myLocation,null,null);
//                }
//
//            }
//        });
//
//        windowManager.addView(view,params);

        ShakeDetector.create(this, () -> {
            sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
            //  mediaPlayer.start();
            shakecounter++;

            if (shakecounter >= SHAKE_THRESHOLD) {
                SendAlert();
                shakecounter = 0;
            }
        });


    }




    private void SendAlert() {

        String ENUM = (sharedPreferences.getString("number", ""));
        manager.sendTextMessage(ENUM, null, "Im in Trouble!\n Please Help!\nSending My Location :\n" + myLocation, null, null);

        String ENUM1 = (sharedPreferences.getString("number1", ""));
        manager.sendTextMessage(ENUM1, null, "Im in Trouble!\n Please Help!\nSending My Location :\n" + myLocation, null, null);


        String ENUM2 = (sharedPreferences.getString("number2", ""));
        manager.sendTextMessage(ENUM2, null, "Im in Trouble!\n Please Help!\nSending My Location :\n" + myLocation, null, null);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent.getAction().equalsIgnoreCase("STOP")) {
            if (isRunning) {
                mediaPlayer.stop();
                this.stopForeground(true);
                this.stopSelf();
//                    windowManager.removeView(view);
                isRunning = false;
            }
        } else {
            Intent notificationIntent = new Intent(this, SmsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("MYID", "CHANNELFOREGROUND", NotificationManager.IMPORTANCE_DEFAULT);

                NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                m.createNotificationChannel(channel);

                Notification notification = new Notification.Builder(this, "MYID")
                        .setContentTitle("Rakshak")
                        .setContentText("Shake Device to Send SOS")
                        .setSmallIcon(R.drawable.girl_logo)
                        .setContentIntent(pendingIntent)
                        .build();
                this.startForeground(115, notification);
                isRunning = true;
                return START_NOT_STICKY;
            }
            if (intent.getAction().equalsIgnoreCase("PLAY")) {
                mediaPlayer.start();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();

        }

    }





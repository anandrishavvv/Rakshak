package com.developer.Safety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class Voice extends AppCompatActivity {
    private SpeechRecognizer speechRecognizer;
    FusedLocationProviderClient fusedLocationClient;
    String myLocation = "";
    private ImageView VoiceStart;
    SharedPreferences sharedPreferences;
    private TextToSpeech textToSpeech;









    SmsManager manager = SmsManager.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);

        VoiceStart=findViewById(R.id.VoiceID);

        // code to get user name
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        String UserName = (sharedPreferences.getString("username", ""));

        // for statusBar Color
        getWindow().setStatusBarColor(ContextCompat.getColor(Voice.this,R.color.teal_700));


        // To get current location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TTS is initialized successfully
                } else {
                    // Handle initialization failure
                }
            }
        });



   VoiceStart.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           Toast.makeText(Voice.this, "Listening Started", Toast.LENGTH_SHORT).show();
           startListening();
       }
   });


        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null && !matches.isEmpty()) {
                    String spokenText = matches.get(0).toLowerCase();

                    if (spokenText.contains("help me") || spokenText.contains("emergency") || spokenText.contains("trouble") || spokenText.contains("bachao") || spokenText.contains(" madad karo") || spokenText.contains(" alert") || spokenText.contains("sandesh")|| spokenText.contains("chood do ")||spokenText.contains("jaane do")) {
                        Toast.makeText(Voice.this, " All right sending alert...", Toast.LENGTH_SHORT).show();
                        speak("thik hai sandesh bheja jaaa rha hai");
                        getlocation();
                       // Replace with your emergency call logic
                    } else if (spokenText.contains("call bhai") || spokenText.contains("call parent") || spokenText.contains("call bhaiya")) {
                        // Implement logic to send a predefined message
                        speak(" Thik hai call kiya jaa rha hai");
                        Toast.makeText(Voice.this, " All right Calling To your Parent", Toast.LENGTH_SHORT).show();
                        delayHandler.removeCallbacksAndMessages(null); // Remove any existing callbacks
                        delayHandler.postDelayed(CallParent, 3000); // Delay of 3 seconds to place call

                    } else if (spokenText.contains("call police")||spokenText.contains("police ko call")|| spokenText.contains("police bulao")) {
                        speak("calling to nearby police");
                        Toast.makeText(Voice.this, " ALl Right Calling to Police....", Toast.LENGTH_SHORT).show();
                        delayHandler.removeCallbacksAndMessages(null); // Remove any existing callbacks
                        delayHandler.postDelayed(CallPolice, 3000); // Delay of 3 seconds to place call

                    } else if (spokenText.contains("app ")|| spokenText.contains("open the app")) {
                        Toast.makeText(Voice.this, " Opening Main Screen", Toast.LENGTH_SHORT).show();
                        speak(" Thik hai khul raha hai");
                        startActivity(new Intent(Voice.this,MainActivity.class));

                    }else if(spokenText.contains("hello")) {
                         Toast.makeText(Voice.this, "Hello "+UserName, Toast.LENGTH_SHORT).show();
                        speak(" Hello "+UserName);

                    } else if(spokenText.contains("who are you")|| spokenText.contains("tum kon ho")) {
                        Toast.makeText(Voice.this, " Mai Aapki Assistant Huu", Toast.LENGTH_SHORT).show();
                        speak(" Mai aapki assistant  hu    bataiyee mai aapki kya sahayata kar sakti hu");

                    }else if(spokenText.contains("tumko kisne banaya hai") || spokenText.contains("who developed you")) {
                            // Toast.makeText(Voice.this, "Maaf kijiye mai nhi smjhi kripya dubara bole", Toast.LENGTH_SHORT).show();
                            speak(" A N college ke chhatroo ne mujhe develop kiya hai taaki mei aapki vikat paristithi me sahayata kar saku ");

                    }else if(spokenText.contains("self defence") || spokenText.contains(" aatmaraksha ")) {
                         Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" ye raha selfdefence ka video jisee dekh kar aap basic tak neek sikh sakte hai");
                        startActivity(new Intent(Voice.this,SelfDefenseActivity.class));

                    }else if(spokenText.contains("contacts") || spokenText.contains(" number ")|| spokenText.contains("sampark")) {
                        Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" yaha aap apne karebiyo ka number darj kare");
                        startActivity(new Intent(Voice.this,ContactActivity.class));

                    }else if(spokenText.contains("law") || spokenText.contains(" kanoon")) {
                        Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" ye rahe kuch basic laws jo aap padh sakte hai");
                        startActivity(new Intent(Voice.this,LawsActivity.class));

                    }else if(spokenText.contains("complain") || spokenText.contains("shikayat")) {
                        Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" yaha aap apna complain darj kare ");
                        startActivity(new Intent(Voice.this,Complaint.class));

                    }else if(spokenText.contains("use")||spokenText.contains("upyog") ) {
                        Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" ye raha video  ");
                        startActivity  8(new Intent(Voice.this,Appusing.class));

                    }else if(spokenText.contains("helpline") ) {
                        Toast.makeText(Voice.this, " Opening....", Toast.LENGTH_SHORT).show();
                        speak(" ye raha kuch mahatwapuurn helpline numbers jispee tap karke app direct call kar sakte hai ");
                        startActivity(new Intent(Voice.this,Helpline.class));
                    }
                    else {
                        speak("Maaf Kijiye maine suna nahi  kripya dubaara kosis kare");
                    }

                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }

            // Implement other methods from RecognitionListener interface
            // as per your requirements.
        });

    }
    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        speechRecognizer.startListening(intent);
    }

    private Handler delayHandler = new Handler();

    private Runnable CallPolice = new Runnable() {
        @Override
        public void run() {
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" +100)));
            startActivity(Incall);
        }
    };

    private Runnable CallParent = new Runnable() {
        @Override
        public void run() {
           call();
             }
    };


    public void getlocation(){
        if (ActivityCompat.checkSelfPermission(Voice.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Voice.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        location.getAltitude();
                        location.getLongitude();
                        myLocation = "http://maps.google.com/maps?q=loc:" + location.getLatitude() + "," + location.getLongitude();
                    } else {
                        myLocation = "Unable to Find Location :(";
                    }
                    sendmessage();
                    sendmessage1();
                    sendmessage2();
                });
    }

    void sendmessage() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number")) {
            String NUM = (sharedPreferences.getString("number", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }
    void sendmessage1() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number1")) {
            String NUM = (sharedPreferences.getString("number1", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n Please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }
    void sendmessage2() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number2")) {
            String NUM = (sharedPreferences.getString("number2", ""));
            manager.sendTextMessage(NUM, null, "Im in Trouble!\n Please Help !\nSending My Location :\n" + myLocation, null, null);
        }
    }

    void call() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number")) {
            String calling = (sharedPreferences.getString("number", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }
    void call1() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number1")) {
            String calling = (sharedPreferences.getString("number1", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }
    void call2() {
        sharedPreferences = getSharedPreferences("Data", MODE_PRIVATE);
        if (sharedPreferences.contains("number2")) {
            String calling = (sharedPreferences.getString("number2", ""));
            Intent Incall = new Intent(Intent.ACTION_CALL);
            Incall.setData((Uri.parse("tel:" + calling)));
            startActivity(Incall);
        }
    }

    // Text To Speech Method
    private void speak(String message) {
        if (textToSpeech != null) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

}
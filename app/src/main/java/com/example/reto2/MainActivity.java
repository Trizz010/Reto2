package com.example.reto2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText inputText;
    private View radioButtonView;
    private NotificationCompat.Builder myBuilder;
    private NotificationManager myNotificationManager;
//    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        dialogo1();
    }
    private void dialogo1(){
        // Primer diálogo
        AlertDialog.Builder conf1 = new AlertDialog.Builder(this);
        conf1.setTitle("Hello! :)");
        conf1.setMessage("Type your name here");
        inputText = new EditText(context);
        conf1.setView(inputText);
        conf1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        conf1.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(inputText.getText().toString().isEmpty()){
                    Toast.makeText(context, "This field cannot be empty", Toast.LENGTH_SHORT).show();
                    dialogo1();
                } else {
                    dialogo2();
                }
            }
        });
        AlertDialog dialog1 = conf1.create();
        dialog1.show();
    }
    private void dialogo2(){
        // Segundo diálogo
        AlertDialog.Builder conf2 = new AlertDialog.Builder(this);
        conf2.setTitle("Hello! :)");
        conf2.setMessage("Select one");
        LayoutInflater inflater = LayoutInflater.from(context);
        radioButtonView = inflater.inflate(R.layout.radiobutton_layout, null);
        conf2.setView(radioButtonView);
        conf2.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogo1();
            }
        });
        conf2.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioGroup radioGroup = radioButtonView.findViewById(R.id.radioGroup);
                if (radioGroup.getCheckedRadioButtonId() == -1){
                    Toast.makeText(context, "You need to select one!!", Toast.LENGTH_SHORT).show();
                    dialogo2();
                } else {
                    notificacion();
                }
            }
        });
        AlertDialog dialog2 = conf2.create();
        dialog2.show();
    }
//    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificacion(){
        NotificationChannel myChannel = new NotificationChannel("CHANNEL_ID", "CHANNEL_NAME", NotificationManager.IMPORTANCE_DEFAULT);

        RadioGroup radioGroup = radioButtonView.findViewById(R.id.radioGroup);
        int id = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRB = radioButtonView.findViewById(id);

        myBuilder = new NotificationCompat.Builder(getApplicationContext(), "CHANNEL_ID")
                .setSmallIcon(R.mipmap.ic_launcher_round) // notification icon
                .setContentTitle(inputText.getText().toString())
                .setContentText(selectedRB.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        myNotificationManager.createNotificationChannel(myChannel);

        myNotificationManager.notify(0, myBuilder.build());

    }
}
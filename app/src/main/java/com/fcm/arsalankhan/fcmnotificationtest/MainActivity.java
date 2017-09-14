package com.fcm.arsalankhan.fcmnotificationtest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mRootRef;
    TextView tvTitle,tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = (TextView) findViewById(R.id.tv_Title);
        tvMessage = (TextView) findViewById(R.id.tv_message);

        mRootRef = FirebaseDatabase.getInstance().getReference().child("message");

        FirebaseMessaging.getInstance().subscribeToTopic("notifications");

        if(getIntent().getExtras()!=null){

            for(String key :  getIntent().getExtras().keySet()){

                if(key.equals("title")){

                    tvTitle.setText("Title: "+getIntent().getExtras().getString(key));
                }else if (key.equals("message")){

                    tvMessage.setText("Message: "+getIntent().getExtras().getString(key));
                }
            }
        }
    }


    public void StoreData(View view){

        EditText editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        EditText editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        String title = editTextTitle.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){

            String randomkey = mRootRef.push().getKey();

            Map messageMap = new HashMap();
            messageMap.put("title",title);
            messageMap.put("message",message);

            mRootRef.child(randomkey).updateChildren(messageMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if(task.isSuccessful()){

                        Toast.makeText(MainActivity.this, "Message Store in database", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else{

            Toast.makeText(this, "Fill the Fields", Toast.LENGTH_SHORT).show();
        }

    }
}

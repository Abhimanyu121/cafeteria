package com.example.abhimanyu.cafeteria;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class register extends AppCompatActivity  {
EditText email,name,phone,pass;
String semail,sname,sphone,spass;
FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText)findViewById(R.id.email);
        name=(EditText)findViewById(R.id.name);

        phone=(EditText)findViewById(R.id.phone);
        pass=(EditText)findViewById(R.id.phone);

        Button button=(Button)findViewById(R.id.butt);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        Button button2=(Button)findViewById(R.id.butt2);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), login.class);
                startActivity(myIntent);
            }
        });

    }

    public void register(){
        semail=email.getText().toString();
        sname=name.getText().toString();
        sphone=phone.getText().toString();
        spass=pass.getText().toString();
        if(semail.equals(null)||sname.equals(null)||sphone.equals(null)||spass.equals(null)){
            Toast.makeText(this,"enter all the values",Toast.LENGTH_LONG);
        }
        else{
            Map<String,Object> values=new HashMap<>();
            values.put("Password",spass);
            values.put("email",semail);
            values.put("name",sname);
            db.collection("users").document(sphone).set(values).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("bleh","done");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e("bleh","Failled",e);
                }
            });
        }
        Intent myIntent = new Intent(this, login.class);
        startActivity(myIntent);
    }




}


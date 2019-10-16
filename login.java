package com.example.abhimanyu.cafeteria;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class login extends AppCompatActivity  {
    EditText ph,pass;
    String sphno="",spass;
//Zhxjfkc



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ph=(EditText)findViewById(R.id.phno);
        pass=(EditText)findViewById(R.id.password);

        Button button=(Button)findViewById(R.id.butt);
        Button button2=(Button)findViewById(R.id.butt2);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), register.class);
                startActivity(myIntent);
            }
        });
        // Set up the login form.
    }
    public void login(){
        sphno=ph.getText().toString();
        spass=pass.getText().toString();
        DocumentReference mDocRef= FirebaseFirestore.getInstance().document("users/"+sphno);
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    String opass=documentSnapshot.getString("Password");
                    String oname=documentSnapshot.getString("name");
                    if(opass.equals(spass)){
                        SharedPreferences.Editor editor = getSharedPreferences("creds", MODE_PRIVATE).edit();
                        editor.putString("name", oname);
                        editor.putString("phno",sphno );
                        editor.apply();

                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);



                    }
                    else{
                        TextView wtv=(TextView)findViewById(R.id.tv2);
                        wtv.setText("wrong credentials");
                    }



                }
            }
        });

    }

}


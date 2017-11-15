package com.example.akash.firebase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;
    private Button buttonLogOut;
    private EditText editTextName;
    private EditText editTextAddress;
    private Button buttonSave;
    private DatabaseReference databaseReference; //----------This will reference for firebase database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
        buttonLogOut = (Button) findViewById(R.id.buttonLogOut);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        buttonSave = (Button) findViewById(R.id.buttonSave);

        databaseReference = FirebaseDatabase.getInstance().getReference();     //---------- This will create instance for database
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewUserEmail.setText("Welcome "+user.getEmail());

        buttonLogOut.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }
        // This method saves user information into firebase database
    private void saveUserInformation(){
        String name = editTextName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        userInformation userinformation = new userInformation(name,address);  //This will pass name and address to another class userInformatio
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformation);   //This will get the id of loged in user and saves that information in his account
        Toast.makeText(getApplicationContext(),"Information Saved",Toast.LENGTH_SHORT).show();
        editTextName.setText("");
        editTextAddress.setText("");
    }

    @Override
    public void onClick(View view) {
        if (view == buttonLogOut){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        if(view == buttonSave){
            saveUserInformation();
        }
    }
}

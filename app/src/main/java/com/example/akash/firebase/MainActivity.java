package com.example.akash.firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth; //-------------------------Added for Authentication using Firebase-----------------//

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private ProgressDialog progressDialog; //----------------------Added for progress dialog------------------------------//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();  //--------------------This will create instance of firebase auth----------//

        //------------This method will check for current user, if user is loged in it will directly show users profile--------//
        if(firebaseAuth.getCurrentUser() !=null){
            //Profile
            finish();
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        }

        buttonRegister = (Button) findViewById(R.id.buttonRegester);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignIn = (TextView) findViewById(R.id.textViewSignIn);
        progressDialog = new ProgressDialog(this);


        buttonRegister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }
    // ------------------------------This method will regester the new user in firebase------------------------------//
    private void regesterUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //Check if email is empty, and give instruction
            Toast.makeText(this,"Email address can not be empty", Toast.LENGTH_SHORT).show();
            //stop function from executing further
            return;
        }

        if(TextUtils.isEmpty(password)){
            //Check if password is empty, and give instruction
            Toast.makeText(this,"Password can not be empty", Toast.LENGTH_SHORT).show();
            //stop function from executing further
            return;
        }
        //Validation is Successful
        //regester a user
        //Show progress bar
        progressDialog.setMessage("Regestering User..!");
        progressDialog.show();
        // Toast.makeText(this,"Regestring",Toast.LENGTH_SHORT).show();


        //------------------This method will create new user in firebase using email and id---------
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Success..",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Failed..",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            regesterUser();
        }

        if (view == textViewSignIn){
            //Open Login Activity Here
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }
}

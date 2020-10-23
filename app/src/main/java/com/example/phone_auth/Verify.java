package com.example.phone_auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify extends AppCompatActivity {
    EditText otp;
    Button otp_verif;
    ProgressBar verifyProg;
    String verifycodebysys;
    FirebaseAuth phoneauth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        otp = findViewById(R.id.otp);
        verifyProg = findViewById(R.id.progressBar);
        otp_verif = findViewById(R.id.otp_ver);

        String phonenumber = getIntent().getStringExtra("Phone no");
        Sendverificationtouser(phonenumber);
    }

    private void Sendverificationtouser(String phonenumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phonenumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallback
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verifycodebysys = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyProg.setVisibility(View.VISIBLE);
                verifycode(code);


            }


        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Verify.this,e.getMessage(),Toast.LENGTH_SHORT).show();

        }

        private void verifycode(String verificationcode){
            PhoneAuthCredential cred = PhoneAuthProvider.getCredential(verifycodebysys, verificationcode);
            signInuserbyCredential(cred);


        }

        private void signInuserbyCredential(PhoneAuthCredential cred) {
            phoneauth.signInWithCredential(cred).addOnCompleteListener(Verify.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Verify.this, "Verification Succesfull", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(Verify.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    };
}
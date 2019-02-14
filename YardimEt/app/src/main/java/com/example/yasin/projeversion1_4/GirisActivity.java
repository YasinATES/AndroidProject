package com.example.yasin.projeversion1_4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GirisActivity extends AppCompatActivity {

    EditText etmail,etsifre;
    Button button,giris;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);

        etmail=findViewById(R.id.etmail);
        etsifre=findViewById(R.id.etsifre);
        button=findViewById(R.id.button);
        giris=findViewById(R.id.button3);

        firebaseAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etmail.getText())){
                    etmail.setError("Email Giriniz.");
                    etmail.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(etmail.getText()).matches()) {
                    etmail.setError("Email Giriniz.");
                    etmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(etsifre.getText())) {
                    etsifre.setError("Sifre Giriniz");
                    etsifre.requestFocus();
                    return;
                }
                else{
                    signIn(etmail.getText().toString(), etsifre.getText().toString());
                }
            }
        });
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GirisActivity.this, KayitOlActivity.class));
            }
        });
    }
    private void signIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(GirisActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(GirisActivity.this, SikayetActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Bilgiler Hatalı. Lütfen Kontrol Ediniz.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}

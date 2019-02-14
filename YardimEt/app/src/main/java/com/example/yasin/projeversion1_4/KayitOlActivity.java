package com.example.yasin.projeversion1_4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class KayitOlActivity extends AppCompatActivity implements View.OnClickListener {

  EditText etad,etmail,etsifre,ettel,etsifreonay;
  Button button;
  private FirebaseAuth mAuth;
  DatabaseReference ref;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_kayit_ol);

    etad=findViewById(R.id.etad);
    etmail=findViewById(R.id.etmail);
    etsifre=findViewById(R.id.etsifre);
    ettel=findViewById(R.id.ettel);
    etsifreonay=findViewById(R.id.etsifreonay);
    button=findViewById(R.id.button);

    mAuth = FirebaseAuth.getInstance();
    ref = FirebaseDatabase.getInstance().getReference();

    button.setOnClickListener(this);
  }


  @Override
  protected void onStart() {
    super.onStart();
    if (mAuth.getCurrentUser() != null) {
      //handle the already login user
    }
  }


  private void KullaniciKaydet(){
    final String ad=etad.getText().toString().trim();
    final String email=etmail.getText().toString().trim();
    final String sifre=etsifre.getText().toString().trim();
    final String tel=ettel.getText().toString().trim();
    final String durum="User";
    String sifreonay=etsifreonay.getText().toString().trim();

    if (ad.isEmpty()) {
      etad.setError("Ad Soyad Giriniz.");
      etad.requestFocus();
      return;
    }
    if (email.isEmpty()) {
      etmail.setError("Email Giriniz.");
      etmail.requestFocus();
      return;
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      etmail.setError("Email Giriniz.");
      etmail.requestFocus();
      return;
    }
    if (tel.isEmpty()) {
      ettel.setError("Telefon Giriniz.");
      ettel.requestFocus();
      return;
    }
    if(tel.length()!=11){
      ettel.setError("Başına 0 ekleyerek 11 haneli numaranızı giriniz.");
      ettel.requestFocus();
      return;
    }
    if (sifre.isEmpty()) {
      etsifre.setError("Sifre Giriniz");
      etsifre.requestFocus();
      return;
    }
    if(!sifreonay.equals(sifre)){
      etsifre.setError("Şifreler uyuşmuyor.");
      etsifreonay.setError("Şifreler uyuşmuyor.");
      return;
    }
    if(sifre.length()<6){
      etsifre.setError("Şifre minimum 6 kadakterden oluşmalıdır.");
      etsifre.requestFocus();
      return;
    }
    if (sifreonay.isEmpty()) {
      etsifreonay.setError("Şifrenizi Tekrar Giriniz.");
      etsifreonay.requestFocus();
      return;
    }
    if(sifreonay.length()<6){
      etsifre.setError("Şifre minimum 6 kadakterden oluşmalıdır.");
      etsifre.requestFocus();
      return;
    }

    mAuth.createUserWithEmailAndPassword(email,sifre)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                  Kullanici kullanici = new Kullanici(
                          ad,
                          email,
                          sifre,
                          durum,
                          tel
                  );
                  ref.child("Kullanicilar").child(mAuth.getCurrentUser().getUid()).setValue(kullanici);
                  Toast.makeText(getApplicationContext(),"Kayıt Başarılı", Toast.LENGTH_LONG).show();
                } else {
                  Toast.makeText(getApplicationContext(),"Kayıt Başarısız.", Toast.LENGTH_LONG).show();
                }
              }
            });
    startActivity(new Intent(KayitOlActivity.this, GirisActivity.class));
  }
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button:
        KullaniciKaydet();
        break;
    }
  }
}

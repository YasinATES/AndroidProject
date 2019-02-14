package com.example.yasin.projeversion1_4;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SikayetActivity extends AppCompatActivity {

    ImageButton imageButton;

    private FirebaseAuth mAuth;
    DatabaseReference ref;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                mAuth.signOut();
                startActivity(new Intent(SikayetActivity.this,GirisActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikayet);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");


        imageButton=findViewById(R.id.imageButton2);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Boolean providersEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (providersEnabled == true) {
                    startActivity(new Intent(SikayetActivity.this,MapsActivity.class));
                }
                else {
                    showAlert();
                    Toast.makeText(getApplicationContext(), "GPS KAPALI", Toast.LENGTH_LONG).show();
                }

            }
        });

        adminControl();
    }

    public void adminControl() {
        if (mAuth.getCurrentUser()!= null) {
            ref=ref.child(mAuth.getCurrentUser().getUid()).child("durum");
            ValueEventListener dinle = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue().toString().equals("Admin")){
                        Intent intent = new Intent(SikayetActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            ref.addValueEventListener(dinle);
        }
        else{
            Intent intent = new Intent(SikayetActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("KONUM ETKİNLEŞTİR")
                .setMessage("Konum Ayarlarınız Kapalı.\nLütfen Konum'u Etkinleştirin ")
                .setPositiveButton("Konum Ayarları", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Çıkış", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

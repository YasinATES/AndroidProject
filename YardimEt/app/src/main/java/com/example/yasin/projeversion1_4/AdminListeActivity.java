package com.example.yasin.projeversion1_4;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class AdminListeActivity extends FragmentActivity implements OnMapReadyCallback {



    private GoogleMap mMap;
    TextView tvkulmail, tvsucTuru, tvaciklama;
    String getLatitude;
    String getLongitude;
    ImageView imagev;
    private Bitmap bitmap;

    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_liste);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvkulmail = findViewById(R.id.tvkulmail);
        tvsucTuru = findViewById(R.id.tvsucTuru);
        tvaciklama = findViewById(R.id.tvaciklama);
        imagev = findViewById(R.id.imagev);


        String getAciklama = getIntent().getExtras().getString("getAciklama");
        String getKulmail = getIntent().getExtras().getString("getKulmail");
        String getSucTuru = getIntent().getExtras().getString("getSucTuru");
        String getImg = getIntent().getExtras().getString("getImg");


        getLatitude = getIntent().getExtras().getString("getLatitude");
        getLongitude = getIntent().getExtras().getString("getLongitude");

        tvsucTuru.setText("Surç Türü: " + getSucTuru);
        tvkulmail.setText("Mail: " + getKulmail);
        tvaciklama.setText("Açıklama: " + getAciklama);

        storageRef = storageReference.child("Yuklenenler").child(getImg);
        try {
            final File localFile = File.createTempFile(getImg, "jpeg");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imagev.setImageBitmap(bitmap);
                    imagev.setVisibility(View.VISIBLE);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(AdminListeActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng suc = new LatLng(Double.parseDouble(getLatitude), Double.parseDouble(getLongitude));
        mMap.addMarker(new MarkerOptions().position(suc).title("Suç Mahali"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(suc));
        mMap.setMinZoomPreference(14.5F);
    }


}

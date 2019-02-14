package com.example.yasin.projeversion1_4;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ListView lstv;
    DatabaseReference ref;
    ArrayList<Ihbar> ihbarlist = new ArrayList<Ihbar>();

    FirebaseAuth mAuth=FirebaseAuth.getInstance();


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
                startActivity(new Intent(AdminActivity.this,GirisActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        lstv = findViewById(R.id.lstv);
        ref = FirebaseDatabase.getInstance().getReference().child("ihbarlar");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ihbarlist.add(new Ihbar(
                            ds.child("sucTuru").getValue().toString(),
                            ds.child("aciklama").getValue().toString(),
                            ds.child("latitude").getValue().toString(),
                            ds.child("longitude").getValue().toString(),
                            ds.child("kulmail").getValue().toString(),
                            ds.child("img").getValue().toString()
                    ));
                }
                UsersAdapter adapter2 = new UsersAdapter(getApplicationContext(), ihbarlist);
                lstv.setAdapter(adapter2);

                lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), AdminListeActivity.class);
                        intent.putExtra("getAciklama", ihbarlist.get(position).getAciklama());
                        intent.putExtra("getKulmail", ihbarlist.get(position).getKulmail());
                        intent.putExtra("getLatitude", ihbarlist.get(position).getLatitude());
                        intent.putExtra("getLongitude", ihbarlist.get(position).getLongitude());
                        intent.putExtra("getSucTuru", ihbarlist.get(position).getSucTuru());
                        intent.putExtra("getImg", ihbarlist.get(position).getImg());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}

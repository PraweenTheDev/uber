package com.example.acer.ubberrrrr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class justforfun extends AppCompatActivity {


    Button btnback,btnok;
    TextView name,pno;
    ImageView propview;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private  String uid;
    private  String mname;
    private  String mpno;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_justforfun);


        name = findViewById(R.id.name);
        pno = findViewById(R.id.pno);
        btnback = findViewById(R.id.back);
        btnok = findViewById(R.id.btnok);
        propview = findViewById(R.id.img);


        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(uid);

        getuserinfo();

        propview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });


        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                saveuserinfo();
            }
        });


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


             finish();
             return;

            }
        });


    }







    private void getuserinfo() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String,Object> map = (Map<String,Object>) dataSnapshot.getValue();
                    if(map.get("name")!= null){
                        mname = map.get("name").toString();
                        name.setText(mname);

                    }

                    if(map.get("phone")!= null){
                        mpno = map.get("phone").toString();
                        pno.setText(mpno);

                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void saveuserinfo() {
        mname = name.getText().toString();
        mpno = pno.getText().toString();

        Map userinfo = new HashMap();
        userinfo.put("name",mname);
        userinfo.put("phone",mpno);
        mDatabase.updateChildren(userinfo);

        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
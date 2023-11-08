
package com.example.customchu;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class home extends AppCompatActivity {

    ImageButton logout, toScanQR, toMap, notificationbtn, profilebtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView greetings, txtCounter;
    DatabaseReference databaseFacility;
    Button incrementBtn;
    int libRoom1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        logout = findViewById(R.id.homebtn);
        greetings = findViewById(R.id.userGreet);
        toScanQR = findViewById(R.id.toScanQR);
        toMap = findViewById(R.id.toMap);
        notificationbtn = findViewById(R.id.notificationbtn);
        profilebtn = findViewById(R.id.profilebtn);

        toScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, QRActivity.class);
                startActivity(intent);
            }
        });

        notificationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, notifActivity.class);
                startActivity(intent);
            }
        });

        profilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, profileActivity.class);
                startActivity(intent);
            }
        });

        toMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, mapActivity.class);
                startActivity(intent);
            }
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account != null){
//            String Name = account.getDisplayName();
//            greetings.setText(Name);
//            Toast.makeText(this, "Login Success, welcome "+ Name, Toast.LENGTH_SHORT).show();
//        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        incrementBtn = findViewById(R.id.incrementBtn);
        txtCounter = findViewById(R.id.txtCounter);

        //getRoom1();
        databaseFacility = FirebaseDatabase.getInstance().getReference();
        DatabaseReference room1 = databaseFacility.child("Room1").child("Current");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtCounter.setText(dataSnapshot.getValue() + "");
                libRoom1 = Integer.parseInt(dataSnapshot.getValue() + "");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        };
        room1.addValueEventListener(postListener);


        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getRoom1();
                room1.setValue(libRoom1 + 1);
            }
        });
    }
    private void getRoom1(){
        databaseFacility = FirebaseDatabase.getInstance().getReference();
        DatabaseReference room1 = databaseFacility.child("Room1").child("Current");
        incrementBtn.setEnabled(false);

        room1.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    txtCounter.setText(task.getResult().getValue() +"");
                    libRoom1 = Integer.parseInt(task.getResult().getValue() + "");
                    incrementBtn.setEnabled(true);
                }
            }
        });

    }

    private void signOut() {
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}

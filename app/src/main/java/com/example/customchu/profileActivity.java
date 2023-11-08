package com.example.customchu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.squareup.picasso.Picasso;

public class profileActivity extends AppCompatActivity {
    ImageButton profileBack;
    ImageView profilePicture;
    EditText firstName, lastName, email;
    Button logout, changePass;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        profileBack = findViewById(R.id.profileBack);
        profilePicture = findViewById(R.id.profilepicture);
        firstName = findViewById(R.id.firstname);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        changePass = findViewById(R.id.changepassword);
        logout = findViewById(R.id.toLogout);

        profileBack.setOnClickListener(view -> finish());
        logout.setOnClickListener(view -> {
            gsc.signOut().addOnCompleteListener(this, task -> {
                // navigate back to home activity
                Intent intent = new Intent(profileActivity.this, MainActivity.class);
                startActivity(intent);
            });
        });

        updateUserinfo();
    }

    private void updateUserinfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // check if this account has been logged in with Google
        // if it is, update the profile picture, and make the fields read-only
        if (account != null) {
            // update the fields
            String profilePicUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";
            Picasso.get()
                    .load(profilePicUrl)
                    .into(profilePicture);

            profilePicture.setImageURI(Uri.parse(profilePicUrl));
            email.setText(account.getEmail());
            firstName.setText(account.getGivenName());
            lastName.setText(account.getFamilyName());

            // make the fields read-only
            email.setEnabled(false);
            firstName.setEnabled(false);
            lastName.setEnabled(false);

            // disable the change password
            changePass.setEnabled(false);
        }
    }
}
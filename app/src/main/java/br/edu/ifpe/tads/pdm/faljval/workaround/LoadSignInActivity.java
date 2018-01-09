package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.User;

public class LoadSignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference drUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_sign_in);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Realizando login...");
        progressDialog.show();

        this.mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        FirebaseUser fbUser = mAuth.getCurrentUser();

        if (fbUser == null) {
            Intent intent = new Intent(LoadSignInActivity.this, SignInActivity.class);
            startActivity(intent);
        }

        drUser = fbDB.getReference("users/" + fbUser.getUid());
        drUser.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User tempUser = dataSnapshot.getValue(User.class);
                if (tempUser != null) {
                    UserAuth.getInstance().setUser(tempUser);
                    Intent intent;
                    Intent serviceN = new Intent(LoadSignInActivity.this, NotificationService.class);

                    if(tempUser.getWorker()) {
                        intent = new Intent(LoadSignInActivity.this, HomeWorkerActivity.class);
                        serviceN.putExtra("WORKER", true);
                    }
                    else {
                        intent = new Intent(LoadSignInActivity.this, HomeActivity.class);
                        serviceN.putExtra("WORKER", false);
                    }
                    progressDialog.dismiss();
                    startService(serviceN);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

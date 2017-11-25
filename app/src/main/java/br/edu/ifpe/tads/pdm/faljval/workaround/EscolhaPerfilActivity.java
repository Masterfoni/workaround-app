package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;

public class EscolhaPerfilActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_perfil);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);
    }

    public void imgbtnWorkerClick(View view) {
        Intent intent = new Intent(this, SignUpWorkerActivity.class);
        startActivity(intent);
    }

    public void imgbtnUserClick(View view) {
        Intent intent = new Intent(this, SignUpUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authListener);
    }
}

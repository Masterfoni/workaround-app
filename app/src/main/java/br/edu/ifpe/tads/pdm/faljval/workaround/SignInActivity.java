package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;

    private EditText edEmail;
    private EditText edPass;

    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        edEmail = (EditText) findViewById(R.id.editLogin);
        edPass = (EditText) findViewById(R.id.editPass);
        btnLogin = (Button) findViewById(R.id.btn_login);
    }

    public void btnSignUpClick(View view) {
        Intent intent = new Intent(this, EscolhaPerfilActivity.class);
        startActivity(intent);
    }

    public void btnSignInClick(View view) {
        String pass = edPass.getText().toString();
        final String email = edEmail.getText().toString();

        if(!validar()) {
            invalidarLogin();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Realizando login...");
        progressDialog.show();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
            progressDialog.dismiss();

            if(!task.isSuccessful()) {
                invalidarLogin();
            }
            }
        });
    }

    public void invalidarLogin() {
        Toast.makeText(getBaseContext(), "Falha ao logar", Toast.LENGTH_LONG).show();

        btnLogin.setEnabled(true);
    }

    public boolean validar() {
        boolean valido = true;

        String email = edEmail.getText().toString();
        String senha = edPass.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Digite um endereço de e-mail válido");
            valido = false;
        } else {
            edEmail.setError(null);
        }

        if (senha.isEmpty()) {
            edPass.setError("Digite sua senha!");
            valido = false;
        } else {
            edPass.setError(null);
        }

        return valido;
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

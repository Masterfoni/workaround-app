package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.User;

public class SignUpUserActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private EditText edEmail;
    private EditText edSenha;
    private EditText edNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        edEmail = (EditText) findViewById(R.id.edit_email);
        edSenha = (EditText) findViewById(R.id.edit_senha);
        edNome = (EditText) findViewById(R.id.edit_nome);
    }

    public void btnCadastrarUserClick(View view) {

        final String email = edEmail.getText().toString();
        final String password = edSenha.getText().toString();
        final String nome = edNome.getText().toString();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful() ? "SIGN UP OK!" : "SIGN UP ERROR!";
                        Toast.makeText(SignUpUserActivity.this, msg, Toast.LENGTH_SHORT).show();

                        if(task.isSuccessful()){
                            User tempUser = new User(nome, email, false);
                            DatabaseReference drUsers = FirebaseDatabase.getInstance()
                                    .getReference("users" );
                            drUsers.child(mAuth.getCurrentUser().getUid()).setValue(tempUser);
                        }
                    }
                });
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

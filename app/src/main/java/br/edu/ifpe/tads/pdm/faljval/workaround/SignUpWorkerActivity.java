package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.auth.FirebaseAuthListener;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class SignUpWorkerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private EditText edEmail;
    private EditText edSenha;
    private EditText edNome;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_worker);

        this.mAuth = FirebaseAuth.getInstance();
        this.authListener = new FirebaseAuthListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spin_tipo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spin_jobs, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                tipo = adapterView.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {            }
        });

        edEmail = (EditText) findViewById(R.id.edit_email);
        edSenha = (EditText) findViewById(R.id.edit_senha);
        edNome = (EditText) findViewById(R.id.edit_nome);
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

    public void btnCadastrarClick(View view) {
        final String email = edEmail.getText().toString();
        final String password = edSenha.getText().toString();
        final String nome = edNome.getText().toString();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String msg = task.isSuccessful() ? "SIGN UP OK!" : "SIGN UP ERROR!";
                        Toast.makeText(SignUpWorkerActivity.this, msg, Toast.LENGTH_SHORT).show();

                        if(task.isSuccessful()){
                            Worker tempWorker = new Worker(nome, email, tipo);
                            DatabaseReference drUsers = FirebaseDatabase.getInstance()
                                    .getReference("workers" );
                            drUsers.child(mAuth.getCurrentUser().getUid()).setValue(tempWorker);
                        }
                    }
                });
    }
}

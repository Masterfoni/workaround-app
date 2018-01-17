package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import br.edu.ifpe.tads.pdm.faljval.workaround.auth.UserAuth;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class SignUpWorkerActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuthListener authListener;
    private EditText edEmail;
    private EditText edSenha;
    private EditText edNome;
    private String tipo;

    private Button botaoCadastrar;

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
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        edEmail = (EditText) findViewById(R.id.edit_email);
        edSenha = (EditText) findViewById(R.id.edit_senha);
        edNome = (EditText) findViewById(R.id.edit_nome);

        botaoCadastrar = (Button) findViewById(R.id.btn_cadastrar_worker);
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

    public boolean validar() {
        boolean valido = true;

        String nome = edNome.getText().toString();
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        if (nome.isEmpty() || nome.length() < 3) {
            edNome.setError("Pelo menos 3 caracteres");
            valido = false;
        } else {
            edNome.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Digite um endereço de e-mail válido");
            valido = false;
        } else {
            edEmail.setError(null);
        }

        if (senha.isEmpty() || senha.length() < 8) {
            edSenha.setError("Pelo menos 8 caracteres");
            valido = false;
        } else {
            edSenha.setError(null);
        }

        return valido;
    }

    public void invalidarCadastro() {
        Toast.makeText(getBaseContext(), "Falha ao cadastrar", Toast.LENGTH_LONG).show();

        botaoCadastrar.setEnabled(true);
    }

    public void btnCadastrarClick(View view) {
        final String email = edEmail.getText().toString();
        final String password = edSenha.getText().toString();
        final String nome = edNome.getText().toString();

        if(!validar()) {
            invalidarCadastro();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(SignUpWorkerActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando sua conta...");
        progressDialog.show();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                String msg = task.isSuccessful() ? "Cadastro realizado com sucesso!!" : "Ocorreu um problema com a requisição!";
                Toast.makeText(SignUpWorkerActivity.this, msg, Toast.LENGTH_SHORT).show();

                if(task.isSuccessful()){
                    Worker newWorker = new Worker();
                    newWorker.setNome(nome);
                    newWorker.setAtividade(tipo);
                    newWorker.setDisponivel(false);
                    newWorker.setEmail(email);
                    newWorker.setWorker(true);
                    newWorker.setNota(0);
                    newWorker.setTotalServices(0);

                    DatabaseReference drUsers = FirebaseDatabase.getInstance().getReference("users" );
                    drUsers.child(mAuth.getCurrentUser().getUid()).setValue(newWorker);

                    UserAuth userAuth = UserAuth.getInstance();
                    userAuth.setUser(newWorker);
                }
            }
        });
    }
}

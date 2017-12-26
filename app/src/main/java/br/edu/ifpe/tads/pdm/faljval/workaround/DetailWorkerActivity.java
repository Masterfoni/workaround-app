package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class DetailWorkerActivity extends AppCompatActivity {

    private DatabaseReference drServicos;
    private String emailWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_worker);

        Intent i = getIntent();
        emailWorker = i.getStringExtra("EMAIL_KEY");
        String nomeWorker = i.getStringExtra("NOME_KEY");
        String atividadeWorker = i.getStringExtra("ATIVIDADE_KEY");

        TextView tvNome = findViewById(R.id.nome_worker_detail);
        TextView tvAtividade = findViewById(R.id.atividade_worker_detail);
        TextView tvEmail = findViewById(R.id.email_worker_detail);

        tvNome.setText(nomeWorker);
        tvAtividade.setText(atividadeWorker);
        tvEmail.setText(emailWorker);
    }

    public void btnCancelSolicitarWorker(View view) {
        finish();
    }

    public void btnSolicitarWorker(View view) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        drServicos = fbDB.getReference();
        Service service = new Service(emailWorker, mAuth.getCurrentUser().getEmail());
        drServicos.child("services").push().setValue(service);
    }
}

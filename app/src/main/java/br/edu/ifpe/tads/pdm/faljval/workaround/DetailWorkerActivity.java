package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class DetailWorkerActivity extends AppCompatActivity {

    private DatabaseReference drServicos;
    private String emailWorker;
    private String nomeWorker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_worker);

        Intent i = getIntent();
        emailWorker = i.getStringExtra("EMAIL_KEY");
        nomeWorker = i.getStringExtra("NOME_KEY");
        String atividadeWorker = i.getStringExtra("ATIVIDADE_KEY");
        float nota =  i.getFloatExtra("NOTA_KEY",0);
        int contagem = i.getIntExtra("CONT_KEY",0);

        TextView tvNome = findViewById(R.id.nome_worker_detail);
        TextView tvAtividade = findViewById(R.id.atividade_worker_detail);
        TextView tvEmail = findViewById(R.id.email_worker_detail);
        TextView tvContagem = findViewById(R.id.services_completos);

        RatingBar rb = findViewById(R.id.notaFinalWorker);
        rb.setRating(nota);

        tvContagem.setText("Serviços completos: " + contagem);
        tvNome.setText(nomeWorker);
        tvAtividade.setText(atividadeWorker);
        tvEmail.setText(emailWorker);
    }

    public void btnSolicitacaoDialog(View view)
    {
        final Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.service_form);
        myDialog.setCancelable(false);
        Button cancelDialog = myDialog.findViewById(R.id.btn_cancelar_svc_form);
        Button solicitaDialog = myDialog.findViewById(R.id.btn_solicitar_svc_form);

        myDialog.show();

        cancelDialog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                myDialog.dismiss();
            }
        });

        solicitaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                solicitarWorker(myDialog);
            }
        });
    }

    public void solicitarWorker(Dialog theDialog) {

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        drServicos = fbDB.getReference();

        EditText etNomeServico = theDialog.findViewById(R.id.et_servico);
        EditText etDescServico = theDialog.findViewById(R.id.et_descservico);
        EditText etLocalServico = theDialog.findViewById(R.id.et_localservico);

        final Service service = new Service(emailWorker, nomeWorker, mAuth.getCurrentUser().getEmail(),
                etNomeServico.getText().toString(),
                etDescServico.getText().toString(),
                etLocalServico.getText().toString());

        final ProgressDialog progressDialog = new ProgressDialog(DetailWorkerActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Realizando solicitação...");
        progressDialog.show();


        drServicos.child("services").push().setValue(service).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                String msg = task.isSuccessful() ? "Solicitação feita com sucesso!" : "Ocorreu um problema com a solicitação!";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

                if(task.isSuccessful()){
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
         finish();
    }
}

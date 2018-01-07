package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.FirebaseHelper;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class DetailServiceActivity extends AppCompatActivity {

    private DatabaseReference drServicos;
    private ArrayList<Service> services;
    private int pos;
    private Service service;
    private String emailWorker;
    private String emailCliente;
    private String nomeServico;
    private String descServico;
    private String localServico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_service);

        Intent i = getIntent();
        pos = i.getIntExtra("POS_KEY", 0);
        emailWorker = i.getStringExtra("WORKER_KEY");
        emailCliente = i.getStringExtra("CLIENTE_KEY");
        nomeServico = i.getStringExtra("NOME_KEY");
        descServico = i.getStringExtra("DESC_KEY");
        localServico = i.getStringExtra("LOCAL_KEY");
        Boolean isAccepted = i.getBooleanExtra("ACC_KEY", false);
        Boolean isWorking = i.getBooleanExtra("WORK_KEY", false);
        Boolean isFinished = i.getBooleanExtra("END_KEY", false);

        TextView tvCliente = findViewById(R.id.cliente_service_detail);
        TextView tvStatus = findViewById(R.id.status_service_detail);
        TextView tvNome = findViewById(R.id.tv_nomeService);
        TextView tvDesc = findViewById(R.id.tv_descService);
        TextView tvLocal = findViewById(R.id.tv_localService);

        Button btnVerde = findViewById(R.id.service_Btn_Verde);
        Button btnVermelho = findViewById(R.id.service_Btn_Vermelho);

        tvCliente.setText(emailCliente);
        tvNome.setText(nomeServico);
        tvDesc.setText(descServico);
        tvLocal.setText(localServico);

        if (!isAccepted && !isFinished && !isWorking) {
            tvStatus.setText("Novo!");

            btnVerde.setText("Aceitar Serviço");
            btnVermelho.setText("Rejeitar Serviço");

            btnVerde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aceitarService(view);
                }
            });

            btnVermelho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejeitarService(view);
                }
            });
        }else if (isAccepted && !isFinished && isWorking) {
            tvStatus.setText("Em execução.");

            btnVerde.setText("Finalizar Serviço");
            btnVermelho.setText("Cancelar");

            btnVerde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalizarService(view);
                }
            });

            btnVermelho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else if (!isAccepted && isFinished && !isWorking) {
            tvStatus.setText("Rejeitado.");

            btnVerde.setText("Ok");
            btnVermelho.setText("Voltar");
            btnVerde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnVermelho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else {
            tvStatus.setText("Finalizado");

            btnVerde.setText("Ok");
            btnVermelho.setText("Voltar");
            btnVerde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

            btnVermelho.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        drServicos = fbDB.getReference("services");
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HomeWorkerActivity.class);
        startActivity(i);
    }

    private void finalizarService(View view) {
        Service service = new Service();
        service.setWorker(emailWorker);
        service.setCliente(emailCliente);
        service.setWorking(false);
        service.setAcepted(true);
        service.setFinished(true);
        drServicos.child(FirebaseHelper.keysServices.get(pos)).setValue(service);
        finish();
    }

    private void rejeitarService(View view) {
        Service service = new Service();
        service.setWorker(emailWorker);
        service.setCliente(emailCliente);
        service.setWorking(false);
        service.setAcepted(false);
        service.setFinished(true);
        drServicos.child(FirebaseHelper.keysServices.get(pos)).setValue(service);
        finish();
    }

    private void aceitarService(View view) {
        Service service = new Service();
        service.setWorker(emailWorker);
        service.setCliente(emailCliente);
        service.setWorking(true);
        service.setAcepted(true);
        service.setFinished(false);
        drServicos.child(FirebaseHelper.keysServices.get(pos)).setValue(service);
        finish();
    }
}

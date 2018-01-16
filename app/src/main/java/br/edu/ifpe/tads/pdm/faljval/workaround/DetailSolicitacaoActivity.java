package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.edu.ifpe.tads.pdm.faljval.workaround.helpers.FirebaseHelper;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.EnumStatusServico;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class DetailSolicitacaoActivity extends AppCompatActivity {

    private int pos;
    private String emailWorker;
    private String nomeWorker;
    private String emailCliente;
    private String nomeServico;
    private String descServico;
    private String localServico;
    private int status;
    private int notaServico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_solicitacao);

        Intent i = getIntent();
        pos = i.getIntExtra("POS_KEY", 0);
        emailWorker = i.getStringExtra("WORKER_KEY");
        nomeWorker = i.getStringExtra("WORKER_NOME_KEY");
        emailCliente = i.getStringExtra("CLIENTE_KEY");
        nomeServico = i.getStringExtra("NOME_KEY");
        descServico = i.getStringExtra("DESC_KEY");
        localServico = i.getStringExtra("LOCAL_KEY");
        notaServico = i.getIntExtra("NOTA_KEY", 0);
        status = i.getIntExtra("STATUS_KEY", EnumStatusServico.PENDING);

        TextView tvWorker = findViewById(R.id.worker_solicitacao_detail);
        TextView tvStatus = findViewById(R.id.status_solicitacao_detail);
        TextView tvNome = findViewById(R.id.tv_nomeSolicitacao);
        TextView tvDesc = findViewById(R.id.tv_descSolicitacao);
        TextView tvLocal = findViewById(R.id.tv_localSolicitacao);
        TextView tvStatus2 = findViewById(R.id.tv_statuSolicitacao);
        RatingBar nota = findViewById(R.id.nota_solicitacao);
        Button btnavliar = findViewById(R.id.btn_avaliar);

        nota.setVisibility(View.INVISIBLE);
        btnavliar.setVisibility(View.INVISIBLE);

        tvWorker.setText(nomeWorker);
        tvNome.setText(nomeServico);
        tvDesc.setText(descServico);
        tvLocal.setText(localServico);
        nota.setNumStars(notaServico);
        String stat = null;

        if (status == EnumStatusServico.PENDING)
            stat="Esperando aceite.";

        else if (status == EnumStatusServico.ACCEPTED)
            stat="Em execução";

        else if (status == EnumStatusServico.REJECTED)
            stat="Rejeitado";

        else if (status == EnumStatusServico.FINISHED)
            stat="Finalizado";

        else if (status == EnumStatusServico.WAITING) {
            stat = "Avalie o Serviço";
            nota.setVisibility(View.VISIBLE);
            btnavliar.setVisibility(View.VISIBLE);
        }

        tvStatus2.setText(stat);
        tvStatus.setText(stat);
    }

    public void btnAvaliarClick(View view) {
        FirebaseDatabase fbDB = FirebaseDatabase.getInstance();
        DatabaseReference drServicos = fbDB.getReference("services");

        RatingBar rb = findViewById(R.id.nota_solicitacao);

        Service service = new Service();
        service.setWorker(emailWorker);
        service.setWorkerNome(nomeWorker);
        service.setCliente(emailCliente);
        service.setStatus(EnumStatusServico.FINISHED);
        service.setDescricao(descServico);
        service.setNome(nomeServico);
        service.setLocal(localServico);
        service.setNota(rb.getNumStars());
        drServicos.child(FirebaseHelper.keysServices.get(pos)).setValue(service);
        finish();
    }
}

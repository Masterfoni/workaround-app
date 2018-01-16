package br.edu.ifpe.tads.pdm.faljval.workaround;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.EnumStatusServico;

public class DetailSolicitacaoActivity extends AppCompatActivity {

    private int pos;
    private String emailWorker;
    private String emailCliente;
    private String nomeServico;
    private String descServico;
    private String localServico;
    private int status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_solicitacao);

        Intent i = getIntent();
        pos = i.getIntExtra("POS_KEY", 0);
        emailWorker = i.getStringExtra("WORKER_KEY");
        emailCliente = i.getStringExtra("CLIENTE_KEY");
        nomeServico = i.getStringExtra("NOME_KEY");
        descServico = i.getStringExtra("DESC_KEY");
        localServico = i.getStringExtra("LOCAL_KEY");
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

        tvWorker.setText(emailWorker);
        tvNome.setText(nomeServico);
        tvDesc.setText(descServico);
        tvLocal.setText(localServico);
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
}

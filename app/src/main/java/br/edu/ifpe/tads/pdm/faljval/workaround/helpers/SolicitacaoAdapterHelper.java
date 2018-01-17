package br.edu.ifpe.tads.pdm.faljval.workaround.helpers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.faljval.workaround.DetailServiceActivity;
import br.edu.ifpe.tads.pdm.faljval.workaround.DetailSolicitacaoActivity;
import br.edu.ifpe.tads.pdm.faljval.workaround.R;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.EnumStatusServico;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class SolicitacaoAdapterHelper extends BaseAdapter{
    private Context c;
    private ArrayList<Service> services;

    public SolicitacaoAdapterHelper (Context c, ArrayList<Service> service) {
        this.c = c;
        this.services = service;
    }

    @Override
    public int getCount() {
        return services.size();
    }

    @Override
    public Object getItem(int i) {
        return services.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(c).inflate(R.layout.model_list_solicitations, viewGroup,false);
        }

        TextView nomeTxt = view.findViewById(R.id.nome_worker_sol_txt);
        TextView descTxt = view.findViewById(R.id.descricao_sol_txt);

        final Service service = (Service) this.getItem(i);

        nomeTxt.setText(service.getWorkerNome());

        if (service.getStatus() == EnumStatusServico.PENDING)
            descTxt.setText("Esperando aceite.");

        else if (service.getStatus() == EnumStatusServico.ACCEPTED)
            descTxt.setText("Em execução");

        else if (service.getStatus() == EnumStatusServico.REJECTED)
            descTxt.setText("Rejeitado");

        else if (service.getStatus() == EnumStatusServico.FINISHED)
            descTxt.setText("Finalizado");

        else if (service.getStatus() == EnumStatusServico.WAITING)
            descTxt.setText("Avalie o Serviço");


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDetailSolicitacao(i, service.getCliente(), service.getWorker(),
        service.getNome(), service.getDescricao(), service.getLocal(),
                service.getStatus(), service.getWorkerNome(), service.getNota());
            }
        });

        return view;
    }

    private void openDetailSolicitacao(int pos, String cliente, String worker, String nome,
                                       String descricao, String local, int status, String nomeWorker, float nota)
    {
        Intent i = new Intent(c, DetailSolicitacaoActivity.class);
        i.putExtra("POS_KEY", pos);
        i.putExtra("CLIENTE_KEY", cliente);
        i.putExtra("WORKER_KEY", worker);
        i.putExtra("NOME_KEY", nome);
        i.putExtra("DESC_KEY", descricao);
        i.putExtra("LOCAL_KEY", local);
        i.putExtra("STATUS_KEY", status);
        i.putExtra("WORKER_NOME_KEY", nomeWorker);
        i.putExtra("NOTA_KEY", nota);
        c.startActivity(i);
    }
}

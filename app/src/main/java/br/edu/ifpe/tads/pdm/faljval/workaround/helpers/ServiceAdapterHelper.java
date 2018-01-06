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
import br.edu.ifpe.tads.pdm.faljval.workaround.R;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;

public class ServiceAdapterHelper extends BaseAdapter{
    private Context c;
    private ArrayList<Service> services;

    public ServiceAdapterHelper (Context c, ArrayList<Service> service) {
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
            view = LayoutInflater.from(c).inflate(R.layout.model_list_services, viewGroup,false);
        }

        TextView nomeTxt = view.findViewById(R.id.nome_worker_txt);
        TextView ativiTxt = view.findViewById(R.id.atividade_worker_txt);

        final Service service = (Service) this.getItem(i);

        nomeTxt.setText(service.getCliente());

        if (!service.isAccepted() && !service.isFinished() && !service.isWorking())
            ativiTxt.setText("Novo");

        if (service.isAccepted() && !service.isFinished() && service.isWorking())
            ativiTxt.setText("Em execução");

        if (!service.isAccepted() && service.isFinished() && !service.isWorking())
            ativiTxt.setText("Rejeitado");

        if (service.isAccepted() && service.isFinished() && !service.isWorking())
            ativiTxt.setText("Finalizado");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AQUI SERÁ A ABERTURA DA TELA DE DETALHAMENTO DO SERVICE + OPÇÕES DE ACEITAR SERVIÇOS
                openDetailService(i, service.getCliente(), service.getWorker(),
                        service.getNome(), service.getDescricao(), service.getLocal(),
                        service.isAccepted(), service.isFinished(),service.isWorking());
            }
        });

        return view;
    }

    private void openDetailService(int pos, String cliente, String worker,
                                   String nome, String descricao, String local,
                                   Boolean ...details)
    {
        Intent i=new Intent(c, DetailServiceActivity.class);
        i.putExtra("POS_KEY",pos);
        i.putExtra("CLIENTE_KEY",cliente);
        i.putExtra("WORKER_KEY",worker);
        i.putExtra("NOME_KEY",nome);
        i.putExtra("DESC_KEY",descricao);
        i.putExtra("LOCAL_KEY",local);
        i.putExtra("ACC_KEY",details[0]);
        i.putExtra("END_KEY",details[1]);
        i.putExtra("WORK_KEY",details[2]);
        c.startActivity(i);
    }
}

package br.edu.ifpe.tads.pdm.faljval.workaround.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpe.tads.pdm.faljval.workaround.R;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Service;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

/**
 * Created by Felipe Lima on 26/12/2017.
 */

public class ServiceAdapterHelper extends BaseAdapter{
    Context c;
    ArrayList<Service> services;
    LayoutInflater wLayoutInflater = null;

    public ServiceAdapterHelper (Context c, ArrayList<Service> service) {
        this.c = c;
        this.services = service;
        wLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(c).inflate(R.layout.model_list_services, viewGroup,false);
        }

        TextView nomeTxt = (TextView) view.findViewById(R.id.nome_worker_txt);
        TextView ativiTxt = (TextView) view.findViewById(R.id.atividade_worker_txt);

        final Service service = (Service) this.getItem(i);

        nomeTxt.setText(service.getCliente());

        if (!service.isAccepted() && !service.isFinished() && !service.isWorking())
            ativiTxt.setText("Novo.");

        if (service.isAccepted() && !service.isFinished() && service.isWorking())
            ativiTxt.setText("Em execução.");

        if (!service.isAccepted() && !service.isFinished() && service.isWorking())
            ativiTxt.setText("Rejeitado.");

        if (service.isAccepted() && service.isFinished() && !service.isWorking())
            ativiTxt.setText("Finalizado");

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //AQUI SERÁ A ABERTURA DA TELA DE DETALHAMENTO DO SERVICE + OPÇÕES DE ACEITAR SERVIÇOS
                //openDetailService();
            }
        });

        return view;
    }
}

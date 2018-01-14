package br.edu.ifpe.tads.pdm.faljval.workaround.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.faljval.workaround.R;
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

        nomeTxt.setText(service.getWorker());
        descTxt.setText(service.getDescricao());

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openDetailSolicitacao();
//            }
//        });

        return view;
    }

//    private void openDetailSolicitacao()
//    {
//    }
}

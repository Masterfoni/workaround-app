package br.edu.ifpe.tads.pdm.faljval.workaround.helpers;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifpe.tads.pdm.faljval.workaround.DetailWorkerActivity;
import br.edu.ifpe.tads.pdm.faljval.workaround.R;
import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.Worker;

public class WorkerAdapterHelper extends BaseAdapter {
    Context c;
    ArrayList<Worker> workers;
    LayoutInflater wLayoutInflater = null;

    public WorkerAdapterHelper(Context c, ArrayList<Worker> workers) {
        this.c = c;
        this.workers = workers;
        wLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return workers.size();
    }

    @Override
    public Object getItem(int pos) {
        return workers.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(c).inflate(R.layout.model_list, viewGroup,false);
        }

        TextView nomeTxt = (TextView) convertView.findViewById(R.id.nome_worker_txt);
        TextView ativiTxt = (TextView) convertView.findViewById(R.id.atividade_worker_txt);

        final Worker worker = (Worker) this.getItem(position);

        nomeTxt.setText(worker.getNome());
        ativiTxt.setText(worker.getAtividade());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(c, "DALE LOKO", Toast.LENGTH_SHORT).show();
                //AQUI SERÁ A ABERTURA DA TELA DE DETALHAMENTO DO WORKER + OPÇÕES DE SOLICITAR SERVIÇOS
                openDetailActivity(worker.getEmail(), worker.getNome(), worker.getAtividade());
            }
        });

        return convertView;
    }

    // ABRE DETAILWORKER
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c, DetailWorkerActivity.class);
        i.putExtra("EMAIL_KEY",details[0]);
        i.putExtra("NOME_KEY",details[1]);
        i.putExtra("ATIVIDADE_KEY",details[2]);
        c.startActivity(i);
    }
}

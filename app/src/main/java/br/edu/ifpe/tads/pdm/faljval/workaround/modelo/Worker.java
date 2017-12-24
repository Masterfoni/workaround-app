package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Felipe Lima on 25/11/2017.
 */

@IgnoreExtraProperties
public class Worker extends User{
    private String atividade;
    private Boolean disponivel;

    public Worker(){}

    public Worker(Worker theWorker) {
        this.atividade = theWorker.atividade;
        this.disponivel = theWorker.disponivel;
        this.setNome(theWorker.getNome());
        this.setEmail(theWorker.getEmail());
        this.setWorker(theWorker.getWorker());
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public Boolean getDisponivel() {
        return disponivel;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
}

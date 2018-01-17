package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Worker extends User{
    private String atividade;
    private Boolean disponivel;
    private float nota;
    private int totalServices;

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

    public float getNota() { return nota; }

    public void setNota(float nota) { this.nota = nota; }

    public int getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(int totalServices) {
        this.totalServices = totalServices;
    }
}

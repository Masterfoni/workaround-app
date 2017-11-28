package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Felipe Lima on 25/11/2017.
 */

@IgnoreExtraProperties
public class Worker {
    private String nome;
    private String email;
    private String atividade;

    public Worker(){}

    public Worker(String nome, String email, String atividade){
        this.nome = nome;
        this.email = email;
        this.atividade = atividade;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getAtividade() {
        return atividade;
    }
}

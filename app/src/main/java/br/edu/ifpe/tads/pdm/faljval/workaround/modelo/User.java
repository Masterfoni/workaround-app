package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Felipe Lima on 25/11/2017.
 */

@IgnoreExtraProperties
public class User  {

    private String nome;
    private String email;
    private boolean worker;
    private String atividade;

    public User () {}

    public User (String nome, String email, boolean worker, String atividade) {
        this.nome = nome;
        this.email = email;
        this.worker = worker;
        this.atividade = atividade;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public boolean isWorker() {
        return worker;
    }

    public String getAtividade() {
        return atividade;
    }
}

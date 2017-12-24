package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Felipe Lima on 25/11/2017.
 */

@IgnoreExtraProperties
public class User  {

    private String nome;
    private String email;
    private Boolean worker;

    public User () {}

    public User (String nome, String email, boolean worker) {
        this.nome = nome;
        this.email = email;
        this.worker = worker;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getWorker() {
        return worker;
    }

    public void setWorker(Boolean worker) {
        this.worker = worker;
    }
}

package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Felipe Lima on 26/12/2017.
 */

@IgnoreExtraProperties
public class Service {

    private String worker;
    private String cliente;
    private String nome;
    private String descricao;
    private String local;
    private int status;

    public Service() {
    }

    public Service(String worker, String cliente, String nome, String descricao, String local) {
        this.worker = worker;
        this.cliente = cliente;
        this.nome = nome;
        this.descricao = descricao;
        this.local = local;
        this.status = EnumStatusServico.PENDING;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}

package br.edu.ifpe.tads.pdm.faljval.workaround.modelo;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EnumStatusServico {

    public static final int ACCEPTED = 0;
    public static final int FINISHED = 1;
    public static final int WORKING = 2;
    public static final int PENDING = 3;
    public static  final int REJECTED = 4;
    public static  final int WAITING = 5;

    public EnumStatusServico(@StatusServico int statusServico) {
        System.out.println("Status :" + statusServico);
    }

    @IntDef({ACCEPTED, FINISHED, WORKING, PENDING, REJECTED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface StatusServico {
    }

    public static void main(String[] args) {
        EnumStatusServico enumStatusServico = new EnumStatusServico(ACCEPTED);
    }
}
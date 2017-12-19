package br.edu.ifpe.tads.pdm.faljval.workaround.auth;

import br.edu.ifpe.tads.pdm.faljval.workaround.modelo.User;

/**
 * Created by Felipe Lima on 19/12/2017.
 */

public class UserAuth {
    private static UserAuth mInstance = null;

    private User user;

    private UserAuth(){
    }

    public static UserAuth getInstance(){
        if(mInstance == null)
        {
            mInstance = new UserAuth();
        }
        return mInstance;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

}

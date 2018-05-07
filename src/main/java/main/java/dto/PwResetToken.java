package main.java.dto;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class PwResetToken {

    @Id
    private int resetToken_id;
    private String pwToken;
    private boolean used;

    public void setPwToken(String pwToken) {
        this.pwToken = pwToken;
    }


    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getPwToken() {
        return pwToken;
    }



}

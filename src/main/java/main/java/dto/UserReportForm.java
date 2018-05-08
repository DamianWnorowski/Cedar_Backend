
package main.java.dto;

import javax.validation.constraints.NotNull;

public class UserReportForm {
    @NotNull
    private int userId;
    @NotNull
    private String description;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}

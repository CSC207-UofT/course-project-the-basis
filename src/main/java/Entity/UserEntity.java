package Entity;

public class UserEntity {

    private String username;
    private String password;

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getUserInfo(){
        return username + "," + password;
    }
}

package sample;

public class Teachers {
    private int user_id;
    private String user_name;
    private String password;
    private String email;
    private String start_date;

    public Teachers(int id, String username, String password, String email, String date) {
        this.user_id = id;
        this.user_name = username;
        this.password = password;
        this.email = email;
        this.start_date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int id) {
        this.user_id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String username) {
        this.user_name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String date) {
        this.start_date = date;
    }

}

package test.dao;

public class UserData {
    private int id;
    private String name;
    private String email;

    public UserData() {
    }

    public UserData(int id, String n, String e) {
        this.id = id;
        name = n;
        email = e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

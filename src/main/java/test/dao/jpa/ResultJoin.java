package test.dao.jpa;

public class ResultJoin {
    String name;
    String post;

    public ResultJoin(String n, String p) {
        name = n;
        post = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

}

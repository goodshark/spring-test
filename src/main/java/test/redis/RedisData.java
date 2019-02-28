package test.redis;

public class RedisData {
    int id;
    String isbn;
    String bookName;
    String author;

    public RedisData() {
    }

    public RedisData(int n, String seq, String bn, String an) {
        id = n;
        isbn = seq;
        bookName = bn;
        author = an;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}

package profchoper.test;


public class User {
    private int id;
    private String first;
    private String last;
    private String email;
    private String city;
    private String company;

    public User() {
    }

    public User(int id, String first, String last, String email, String city, String company) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
        this.city = city;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
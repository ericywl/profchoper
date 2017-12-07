package profchoper.course;


public class Course {
    private String id;
    private String name;
    private String alias;

    public Course() {
        // empty constructor
    }

    public Course(String id, String name, String alias) {
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    @Override
    public String toString() {
        return alias;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }
}

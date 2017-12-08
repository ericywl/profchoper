package profchoper.test;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "public.customer")
public class Customer implements Serializable{

    private static final long serialVersionUID = -3009157732242241606L;
    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setFirstname(String firstName){
        this.firstname = firstName;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public void setLastname(String lastName){
        this.lastname = lastName;
    }

    public String getLastname(){
        return this.lastname;
    }

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstname = firstName;
        this.lastname = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstname, lastname);
    }
}

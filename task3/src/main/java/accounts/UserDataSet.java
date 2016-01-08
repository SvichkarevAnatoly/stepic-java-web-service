package accounts;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login", unique = true, updatable = false)
    private String login;

    @Column(name = "pass", unique = false, updatable = false)
    private String pass;

    //Important to Hibernate!
    public UserDataSet() {
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "id=" + id + ", " +
                "login='" + login + "', " +
                "pass='" + pass + "'" +
                '}';
    }
}

package accounts;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class SessionDataSet implements Serializable {
    private static final long serialVersionUID = -8706689714326132798L;

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name = "session", unique = true, updatable = false)
    private String session;

    //Important to Hibernate!
    public SessionDataSet() {
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "userId=" + userId + ", " +
                "session='" + session + "'" +
                '}';
    }
}

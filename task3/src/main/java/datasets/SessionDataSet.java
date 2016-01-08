package datasets;

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
    @SuppressWarnings("UnusedDeclaration")
    public SessionDataSet() {
    }

    public SessionDataSet(long userId, String session) {
        setUserId(userId);
        setSession(session);
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "userId=" + userId + ", " +
                "session='" + session + "'" +
                '}';
    }
}

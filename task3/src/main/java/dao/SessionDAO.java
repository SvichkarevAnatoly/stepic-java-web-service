package dao;

import datasets.SessionDataSet;
import datasets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class SessionDAO {
    private Session session;

    public SessionDAO(Session session) {
        this.session = session;
    }

    public long insertSession(long userId, String sessionId) throws HibernateException {
        return (Long) session.save(new SessionDataSet(userId, sessionId));
    }


    public UserDataSet getUserDataSetBySessionId(String sessionId) {
        Criteria sessionCriteria = session.createCriteria(SessionDataSet.class);
        final long userId = ((SessionDataSet) sessionCriteria.add(
                Restrictions.eq("session", sessionId))
                .uniqueResult()).getUserId();

        final Criteria userCriteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) userCriteria.add(Restrictions.eq("id", userId)).uniqueResult();
    }
}

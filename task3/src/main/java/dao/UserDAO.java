package dao;

import datasets.UserDataSet;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public UserDataSet getUserDataSetByLogin(String login) throws HibernateException {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("login", login)).uniqueResult();
    }

    public UserDataSet getUserDataSetByUserId(long userId) {
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("id", userId)).uniqueResult();
    }

    public long insertUser(UserDataSet user) throws HibernateException {
        return (Long) session.save(user);
    }
}

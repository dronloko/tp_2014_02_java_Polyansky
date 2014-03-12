package server.database;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by dronloko on 02.03.14.
 */


public class UserDataSetDAO {

    public void save(UserDataSet dataSet) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction trx = session.beginTransaction();
        session.save(dataSet);
        trx.commit();
        session.close();
    }

    public UserDataSet readByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (UserDataSet) criteria.add(Restrictions.eq("login", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserDataSet> readAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(UserDataSet.class);
        return (List<UserDataSet>) criteria.list();
    }
}
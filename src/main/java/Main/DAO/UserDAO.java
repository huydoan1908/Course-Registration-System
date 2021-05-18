package Main.DAO;

import Main.POJO.User;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {
    public static List<User> getAllUser()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<User> user = null;
        try
        {
            final String hql = "from User ";
            Query query = session.createQuery(hql);
            user=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return user;
    }
}

package Main.DAO;

import Main.POJO.User;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SessionDAO {
    static Session session ;
    public static List<Main.POJO.Session> getAllSession()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Main.POJO.Session> res = null;
        try
        {
            final String hql = "from Session ";
            Query query = session.createQuery(hql);
            res=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return res;
    }
}

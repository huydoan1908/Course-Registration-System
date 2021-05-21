package Main.DAO;

import Main.POJO.User;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {
    static Session session ;
    public static List<User> getAllUser()
    {
        session = HibernateUtil.getSessionFactory().openSession();
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

    public static List<User> getAllTeacher()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> teacher = null;
        try
        {
            final String hql = "from User where roleId = 'TCH'";
            Query query = session.createQuery(hql);
            teacher=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return teacher;
    }

    public static List<User> getAllStudent()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> teacher = null;
        try
        {
            final String hql = "from User where roleId = 'STU'";
            Query query = session.createQuery(hql);
            teacher=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return teacher;
    }

    public static void addUser(User user)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        catch(HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
    }
    public static void deleteUser(User user)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
    }

    public static void updateUser(User user)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        catch(HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
    }
}

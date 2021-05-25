package Main.DAO;

import Main.POJO.ClassInfo;
import Main.POJO.Clazz;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ClassDAO {
    static Session session ;
    public static List<Clazz> getAllClass()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Clazz> clazz = null;
        try
        {
            final String hql = "from Clazz";
            Query query = session.createQuery(hql);
            clazz=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return clazz;
    }

    public static void addClass(Clazz clazz)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(clazz);
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
    public static void deleteClass(Clazz clazz)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(clazz);
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

    public static void updateClass(Clazz clazz)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(clazz);
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

    public static List<Clazz> getClassById(String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Clazz> clazz = null;
        try
        {
            final String hql = "from Clazz where classId like '%" + id+ "%'";
            Query query = session.createQuery(hql);
            clazz=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return clazz;
    }

    public static void addStudentInClass(ClassInfo classInfo)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(classInfo);
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

    public static void deleteStudentInClass(ClassInfo classInfo)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(classInfo);
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

    public static List<ClassInfo> getAllInfoClass()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<ClassInfo> info = null;
        try
        {
            final String hql = "from ClassInfo ";
            Query query = session.createQuery(hql);
            info=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return info;
    }

    public static Integer getMale(String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        Integer male=0;
        try
        {
            String hql = "select count(*) " +
                    "from ClassInfo a, User  b " +
                    "where a.studentId = b.id " +
                    "and a.classId = '" + id+"' " +
                    "and b.gender = false " +
                    "and b.roleId = 'STU'";
            Query query = session.createQuery(hql);
            Object res = query.getSingleResult();
            male = ((Long)res).intValue();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return male;
    }
}

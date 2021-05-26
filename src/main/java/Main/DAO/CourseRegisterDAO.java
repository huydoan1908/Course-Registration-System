package Main.DAO;

import Main.POJO.CourseRegister;
import Main.POJO.CourseRegisterInfo;
import Main.POJO.CourseRegister;
import Main.POJO.Semester;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CourseRegisterDAO {
    static Session session ;
    public static List<CourseRegister> getAll()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseRegister> info = null;
        try
        {
            final String hql = "from CourseRegister";
            Query query = session.createQuery(hql);
            info = query.list();
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
    public static List<CourseRegisterInfo> getAllRegister()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseRegisterInfo> info = new ArrayList<CourseRegisterInfo>();
        try
        {
            final String hql = "from CourseRegister c, Semester  s where c.semId = s.semId";
            Query query = session.createQuery(hql);
            List<Object[]> res =query.list();
            for(int i = 0;i<res.size();i++)
            {
                info.add(new CourseRegisterInfo(((CourseRegister)res.get(i)[0]).getRegistId(),
                        ((Semester)res.get(i)[1]).getSemName(),
                        ((Semester)res.get(i)[1]).getSemYear(),
                        ((CourseRegister)res.get(i)[0]).getStartDate(),
                        ((CourseRegister)res.get(i)[0]).getEndDate()));
            }
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

    public static List<CourseRegisterInfo> getAllRegisterById(String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseRegisterInfo> info = new ArrayList<CourseRegisterInfo>();
        try
        {
            final String hql = "from CourseRegister c, Semester  s where c.semId = s.semId " +
                    "and c.semId ='" + id +"'";
            Query query = session.createQuery(hql);
            List<Object[]> res =query.list();
            for(int i = 0;i<res.size();i++)
            {
                info.add(new CourseRegisterInfo(((CourseRegister)res.get(i)[0]).getRegistId(),
                        ((Semester)res.get(i)[1]).getSemName(),
                        ((Semester)res.get(i)[1]).getSemYear(),
                        ((CourseRegister)res.get(i)[0]).getStartDate(),
                        ((CourseRegister)res.get(i)[0]).getEndDate()));
            }
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

    public static void addCourseRegister(CourseRegister courseRegister)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(courseRegister);
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
    public static void deleteCourseRegister(CourseRegister courseRegister)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(courseRegister);
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

    public static void updateCourseRegister(CourseRegister courseRegister)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(courseRegister);
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

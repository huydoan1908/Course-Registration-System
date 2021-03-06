package Main.DAO;

import Main.POJO.*;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class StudentDAO {
    static Session session;

    public static List<User> getAllStudentInCLass(String class_Id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> student = null;
        try
        {
            final String hql = "select s from User s, ClassInfo c where s.id = c.studentId " +
                    "and c.classId = '" +class_Id+ "' " +
                    "and s.roleId = 'STU'";
            Query query = session.createQuery(hql);
            student=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return student;
    }

    public static List<User> getAllStudentInCLassById(String class_Id,String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> student = null;
        try
        {
            final String hql = "select s from User s, ClassInfo c where s.id = c.studentId " +
                    "and c.classId = '" +class_Id+ "' " +
                    "and s.roleId = 'STU'" +
                    "and s.id like '%" + id +"%'";
            Query query = session.createQuery(hql);
            student=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return student;
    }

    public static int countCourse(String stuId, int semId)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        int res = 0;
        try
        {
            final String hql = "select count(a.courseId) from Course c, Attend a where c.courseId = a.courseId " +
                    "and a.studentId = '" +stuId+ "' " +
                    "and c.semiId = " +semId;
            Query query = session.createQuery(hql);
            res = ((Long)query.getSingleResult()).intValue();
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

    public static List<User> getAllStudentInCourse(Integer courseId)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> student = null;
        try
        {
            final String hql = "select s from User s, Attend a where s.id = a.studentId " +
                    "and a.courseId = '" + courseId+"' " +
                    "and s.roleId = 'STU' ";
            Query query = session.createQuery(hql);
            student=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return student;
    }

    public static List<User> getAllStudentInCourseById(Integer courseId, String stuId)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<User> student = null;
        try
        {
            final String hql = "select s from User s, Attend a where s.id = a.studentId " +
                    "and a.courseId = '" + courseId+"' " +
                    "and s.roleId = 'STU' " +
                    "and s.id like '%" + stuId + "%'";
            Query query = session.createQuery(hql);
            student=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return student;
    }

    public static List<Attend> getAllAttendOfStudent(String stuId)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Attend> attends = null;
        try
        {
            final String hql = "From Attend  where studentId = '" + stuId+"'";
            Query query = session.createQuery(hql);
            attends=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return attends;
    }
}

package Main.DAO;

import Main.POJO.*;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    static Session session ;
    public static List<Course> getAll()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> course = null;
        try
        {
            final String hql = "from Course";
            Query query = session.createQuery(hql);
            course = query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return course;
    }

    public static List<Course> getAllBySubject(String subj)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Course> course = null;
        try
        {
            final String hql = "from Course where subjId ='"+subj+"'";
            Query query = session.createQuery(hql);
            course = query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return course;
    }

    public static List<CourseInfo> getAllCourseInSemById(Integer sem, String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseInfo> info = new ArrayList<CourseInfo>();
        try
        {
            final String hql = "from Course c, Semester s, Subject sj, Session ss " +
                    "where c.semiId = s.semId " +
                    "and c.subjId = sj.subjectId " +
                    "and c.sessId = ss.sessId " +
                    "and c.semiId = "+ sem.toString() +
                    " and c.subjId like '%" +id+"%'";
            Query query = session.createQuery(hql);
            List<Object[]> res =query.list();
            for(int i = 0;i<res.size();i++)
            {
                info.add(new CourseInfo(((Course)res.get(i)[0]).getCourseId(),
                        ((Subject)res.get(i)[2]).getSubjectId(),
                        ((Subject)res.get(i)[2]).getSubjectName(),
                        ((Subject)res.get(i)[2]).getCredits(),
                        ((Course)res.get(i)[0]).getTeacher(),
                        ((Course)res.get(i)[0]).getRoom(),
                        ((Course)res.get(i)[0]).getDayOfWeek(),
                        ((Main.POJO.Session)res.get(i)[3]).toString(),
                        ((Course)res.get(i)[0]).getMaxSlot(),
                        ((Course)res.get(i)[0]).getCurrent()));
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

    public static List<CourseInfo> getAllCourseInSem(Integer sem)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseInfo> info = new ArrayList<CourseInfo>();
        try
        {
            final String hql = "from Course c, Semester s, Subject sj, Session ss " +
                    "where c.semiId = s.semId " +
                    "and c.subjId = sj.subjectId " +
                    "and c.sessId = ss.sessId " +
                    "and c.semiId = "+ sem.toString();
            Query query = session.createQuery(hql);
            List<Object[]> res =query.list();
            for(int i = 0;i<res.size();i++)
            {
                info.add(new CourseInfo(((Course)res.get(i)[0]).getCourseId(),
                        ((Subject)res.get(i)[2]).getSubjectId(),
                        ((Subject)res.get(i)[2]).getSubjectName(),
                        ((Subject)res.get(i)[2]).getCredits(),
                        ((Course)res.get(i)[0]).getTeacher(),
                        ((Course)res.get(i)[0]).getRoom(),
                        ((Course)res.get(i)[0]).getDayOfWeek(),
                        ((Main.POJO.Session)res.get(i)[3]).toString(),
                        ((Course)res.get(i)[0]).getMaxSlot(),
                        ((Course)res.get(i)[0]).getCurrent()));
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

    public static List<CourseInfo> getAllCourseOfStudent(Integer sem, String stuId)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<CourseInfo> info = new ArrayList<CourseInfo>();
        try
        {
            final String hql = "from Course c, Semester s, Subject sj, Session ss, Attend a " +
                    "where c.semiId = s.semId " +
                    "and c.subjId = sj.subjectId " +
                    "and c.sessId = ss.sessId " +
                    "and a.courseId = c.courseId " +
                    "and c.semiId = "+ sem.toString() +
                    " and a.studentId ='" +stuId+"'";
            Query query = session.createQuery(hql);
            List<Object[]> res =query.list();
            for(int i = 0;i<res.size();i++)
            {
                info.add(new CourseInfo(((Course)res.get(i)[0]).getCourseId(),
                        ((Subject)res.get(i)[2]).getSubjectId(),
                        ((Subject)res.get(i)[2]).getSubjectName(),
                        ((Subject)res.get(i)[2]).getCredits(),
                        ((Course)res.get(i)[0]).getTeacher(),
                        ((Course)res.get(i)[0]).getRoom(),
                        ((Course)res.get(i)[0]).getDayOfWeek(),
                        ((Main.POJO.Session)res.get(i)[3]).toString(),
                        ((Course)res.get(i)[0]).getMaxSlot(),
                        ((Course)res.get(i)[0]).getCurrent()));
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

    public static void addCourse(Course course)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(course);
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
    public static void deleteCourse(Course course)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(course);
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

    public static void addAttend(Attend attend)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(attend);
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

    public static void deleteAttend(Attend attend)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(attend);
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
}

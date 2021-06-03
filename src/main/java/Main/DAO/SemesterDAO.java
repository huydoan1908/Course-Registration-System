package Main.DAO;

import Main.POJO.Semester;
import Main.Util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SemesterDAO {
    static Session session ;
    public static List<Semester> getAllSemester()
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semester = null;
        try
        {
            final String hql = "from Semester";
            Query query = session.createQuery(hql);
            semester=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return semester;
    }

    public static void addSemester(Semester semester)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(semester);
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
    public static void deleteSemester(Semester semester)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try
        {
            session.beginTransaction();
            session.delete(semester);
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

    public static void updateSemester(Semester semester)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.update(semester);
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

    public static List<Semester> getSemesterById(String id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semester = null;
        try
        {
            final String hql = "from Semester where semName like '%" + id+ "%'";
            Query query = session.createQuery(hql);
            semester=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return semester;
    }

    public static Semester getSemesterById(int id)
    {
        session = HibernateUtil.getSessionFactory().openSession();
        List<Semester> semester = null;
        try
        {
            final String hql = "from Semester where semId = '" + id+ "'";
            Query query = session.createQuery(hql);
            semester=query.list();
        }
        catch (HibernateException e)
        {
            System.out.println(e);
        }
        finally {
            session.close();
        }
        return semester.get(0);
    }
    public static void writeFile(int semId) throws IOException {
        FileOutputStream file = new FileOutputStream("Sem.bin");
        file.write(semId);
        file.close();
    }

    public static int readFile(){
        int res =-1;
        try {
            FileInputStream file = new FileInputStream("Sem.bin");
            if(file.available() == 0)
                return res;
            res=file.read();
            return res;
        } catch (IOException e) {
            return -1;
        }

    }
}

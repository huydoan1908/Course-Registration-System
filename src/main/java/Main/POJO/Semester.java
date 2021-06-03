package Main.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Semester {
    private int semId;
    private String semName;
    private Integer semYear;
    private Date startDate;
    private Date endDate;

    public Semester() {
    }

    @Id
    @Column(name = "sem_id", nullable = false)
    public int getSemId() {
        return semId;
    }

    public void setSemId(int semId) {
        this.semId = semId;
    }

    @Basic
    @Column(name = "sem_name", nullable = true, length = 3)
    public String getSemName() {
        return semName;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    @Basic
    @Column(name = "sem_year", nullable = true)
    public Integer getSemYear() {
        return semYear;
    }

    public void setSemYear(Integer semYear) {
        this.semYear = semYear;
    }

    @Basic
    @Column(name = "start_date", nullable = true)
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "end_date", nullable = true)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Semester semester = (Semester) o;
        return semId == semester.semId && Objects.equals(semName, semester.semName) && Objects.equals(semYear, semester.semYear) && Objects.equals(startDate, semester.startDate) && Objects.equals(endDate, semester.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semId, semName, semYear, startDate, endDate);
    }

    @Override
    public String toString() {
        return semName +" - " +semYear;
    }
}

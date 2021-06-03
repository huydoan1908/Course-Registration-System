package Main.POJO;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "course_register", schema = "manage student", catalog = "")
public class CourseRegister {
    private int registId;
    private Integer semId;
    private Date startDate;
    private Date endDate;

    public CourseRegister() {
    }

    @Id
    @Column(name = "regist_id", nullable = false)
    public int getRegistId() {
        return registId;
    }

    public void setRegistId(int registId) {
        this.registId = registId;
    }

    @Basic
    @Column(name = "sem_id", nullable = true)
    public Integer getSemId() {
        return semId;
    }

    public void setSemId(Integer semId) {
        this.semId = semId;
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
        CourseRegister that = (CourseRegister) o;
        return registId == that.registId && Objects.equals(semId, that.semId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registId, semId, startDate, endDate);
    }
}

package Main.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Attend {
    private int attendId;
    private String studentId;
    private String subjId;
    private Integer semId;

    @Id
    @Column(name = "attend_id", nullable = false)
    public int getAttendId() {
        return attendId;
    }

    public void setAttendId(int attendId) {
        this.attendId = attendId;
    }

    @Basic
    @Column(name = "student_id", nullable = true, length = 8)
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "subj_id", nullable = true, length = 5)
    public String getSubjId() {
        return subjId;
    }

    public void setSubjId(String subjId) {
        this.subjId = subjId;
    }

    @Basic
    @Column(name = "sem_id", nullable = true)
    public Integer getSemId() {
        return semId;
    }

    public void setSemId(Integer semId) {
        this.semId = semId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attend attend = (Attend) o;
        return attendId == attend.attendId && Objects.equals(studentId, attend.studentId) && Objects.equals(subjId, attend.subjId) && Objects.equals(semId, attend.semId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendId, studentId, subjId, semId);
    }
}

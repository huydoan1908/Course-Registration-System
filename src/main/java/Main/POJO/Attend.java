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
    private Integer courseId;

    public Attend() {
    }

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
    @Column(name = "course_id", nullable = true)
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attend attend = (Attend) o;
        return attendId == attend.attendId && Objects.equals(studentId, attend.studentId) && Objects.equals(courseId, attend.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendId, studentId, courseId);
    }
}

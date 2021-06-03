package Main.POJO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class_info", schema = "manage student", catalog = "")
public class ClassInfo {
    private int infoId;
    private String studentId;
    private String classId;

    public ClassInfo() {
    }

    @Id
    @Column(name = "info_id", nullable = false)
    public int getInfoId() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId = infoId;
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
    @Column(name = "class_id", nullable = true, length = 8)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassInfo classInfo = (ClassInfo) o;
        return infoId == classInfo.infoId && Objects.equals(studentId, classInfo.studentId) && Objects.equals(classId, classInfo.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(infoId, studentId, classId);
    }
}

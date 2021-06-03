package Main.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Subject {
    private String subjectId;
    private String subjectName;
    private Integer credits;

    public Subject() {
    }

    @Id
    @Column(name = "subject_id", nullable = false, length = 5)
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    @Basic
    @Column(name = "subject_name", nullable = true, length = 30)
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Basic
    @Column(name = "credits", nullable = true)
    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectId, subject.subjectId) && Objects.equals(subjectName, subject.subjectName) && Objects.equals(credits, subject.credits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId, subjectName, credits);
    }

    @Override
    public String toString() {
        return subjectId + "-" + subjectName;
    }
}

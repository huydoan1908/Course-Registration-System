package Main.POJO;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "class", schema = "manage student", catalog = "")
public class Clazz {
    private String classId;
    private Integer total;
    private Integer male;
    private Integer female;

    @Id
    @Column(name = "class_id", nullable = false, length = 8)
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Basic
    @Column(name = "total", nullable = true)
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Basic
    @Column(name = "male", nullable = true)
    public Integer getMale() {
        return male;
    }

    public void setMale(Integer male) {
        this.male = male;
    }

    @Basic
    @Column(name = "female", nullable = true)
    public Integer getFemale() {
        return female;
    }

    public void setFemale(Integer female) {
        this.female = female;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return Objects.equals(classId, clazz.classId) && Objects.equals(total, clazz.total) && Objects.equals(male, clazz.male) && Objects.equals(female, clazz.female);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId, total, male, female);
    }
}

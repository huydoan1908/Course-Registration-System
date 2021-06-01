package Main.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Course {
    private int courseId;
    private String subjId;
    private Integer semiId;
    private String teacher;
    private String room;
    private String dayOfWeek;
    private String sessId;
    private Integer maxSlot;
    private Integer current;

    public Course() {
    }

    public Course(int courseId, Integer current) {
        this.courseId = courseId;
        this.current = current;
    }

    @Id
    @Column(name = "course_id", nullable = false)
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
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
    @Column(name = "semi_id", nullable = true)
    public Integer getSemiId() {
        return semiId;
    }

    public void setSemiId(Integer semiId) {
        this.semiId = semiId;
    }

    @Basic
    @Column(name = "teacher", nullable = true, length = 30)
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Basic
    @Column(name = "room", nullable = true, length = 4)
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Basic
    @Column(name = "dayOfWeek", nullable = true, length = 3)
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Basic
    @Column(name = "sess_id", nullable = true, length = 1)
    public String getSessId() {
        return sessId;
    }

    public void setSessId(String sessId) {
        this.sessId = sessId;
    }

    @Basic
    @Column(name = "max_slot", nullable = true)
    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    @Basic
    @Column(name = "current", nullable = true)
    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Objects.equals(subjId, course.subjId) && Objects.equals(semiId, course.semiId) && Objects.equals(teacher, course.teacher) && Objects.equals(room, course.room) && Objects.equals(dayOfWeek, course.dayOfWeek) && Objects.equals(sessId, course.sessId) && Objects.equals(maxSlot, course.maxSlot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, subjId, semiId, teacher, room, dayOfWeek, sessId, maxSlot);
    }
}

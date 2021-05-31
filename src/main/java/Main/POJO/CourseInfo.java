package Main.POJO;

import javafx.scene.control.CheckBox;

public class CourseInfo {
    private Integer courseId;
    private String subjectId;
    private String subjectName;
    private Integer credits;
    private String teacher;
    private String room;
    private String dayOfWeek;
    private String session;
    private Integer maxSlot;
    private Integer current;
    private CheckBox select;

    public CourseInfo() { }

    public CourseInfo(Integer courseId, String subjectId, String subjectName, Integer credits, String teacher, String room, String dayOfWeek, String session, Integer maxSlot, Integer current) {
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.credits = credits;
        this.teacher = teacher;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
        this.session = session;
        this.maxSlot = maxSlot;
        this.current = current;
        this.select= new CheckBox();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Integer getMaxSlot() {
        return maxSlot;
    }

    public void setMaxSlot(Integer maxSlot) {
        this.maxSlot = maxSlot;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }
}

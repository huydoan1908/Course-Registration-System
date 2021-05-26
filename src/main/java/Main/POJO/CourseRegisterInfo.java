package Main.POJO;

import java.sql.Date;

public class CourseRegisterInfo {
    private int id;
    private String semName;
    private Integer semYear;
    private Date start;
    private Date end;

    public CourseRegisterInfo() {
    }

    public CourseRegisterInfo(int id, String semName, Integer semYear, Date start, Date end) {
        this.id = id;
        this.semName = semName;
        this.semYear = semYear;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSemName() {
        return semName;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    public Integer getSemYear() {
        return semYear;
    }

    public void setSemYear(Integer semYear) {
        this.semYear = semYear;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}

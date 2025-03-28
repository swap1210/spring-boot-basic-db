package dev.bedesi.sms.schoolmanagementsystem.DAO;


import lombok.Data;

@Data
public class CourseDao {
    private int id;
    private String name;
    private boolean active;
    public CourseDao(int id, String name,boolean active) {
        this.id = id;
        this.name = name;
        this.active=active;
    }
}

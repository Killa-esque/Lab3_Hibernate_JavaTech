package Pojo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Manufacture {
    private int id;
    private String name;
    private String location;
    private int employee;

    public Manufacture(String name, String location, int employee) {
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    @OneToMany(mappedBy = "manufacture")
    private List<Phone> phones;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "Employeee")
    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Manufacture{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employee=" + employee +
                ", phones=" + phones +
                '}';
    }
}

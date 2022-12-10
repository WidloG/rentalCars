package database;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Cars")

public class Cars {
    private int id;
    private String brand;
    private String model;
    private double power;
    private double price;

    @OneToMany(mappedBy = "carId")
    private Set<Rents> Rents = new HashSet<>(0);

    public Cars() {}

    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Brand")
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Basic
    @Column(name = "Model")
    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "Power")
    public double getPower() {
        return power;
    }
    public void setPower(double power) {
        this.power = power;
    }

    @Basic
    @Column(name = "Price")
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

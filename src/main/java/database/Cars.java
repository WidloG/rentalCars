package database;


import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "Cars")

public class Cars {
    @Column(name = "carsId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "Brand")
    private String brand;
    @Basic
    @Column(name = "Model")
    private String model;
    @Basic
    @Column(name = "Power")
    private double power;
    @Basic
    @Column(name = "Price")
    private double price;

    @OneToMany(mappedBy = "cars")
    private Set<Rents> Rents = new HashSet<>(0);

    public Cars() {}



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public double getPower() {
        return power;
    }
    public void setPower(double power) {
        this.power = power;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cars cars = (Cars) o;
        return id == cars.id && Double.compare(cars.power, power) == 0 && Double.compare(cars.price, price) == 0 && brand.equals(cars.brand) && model.equals(cars.model) && Rents.equals(cars.Rents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, brand, model, power, price, Rents);
    }
}

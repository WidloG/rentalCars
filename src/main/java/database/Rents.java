package database;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Rents")

public class Rents {

    private int id;
    private Date dateOfRent;
    private Date dateOfReturn;
    private double rentPrice;
    @ManyToOne
    @JoinColumn(name="carID")
    private Cars carId;
    @ManyToOne
    @JoinColumn(name="clientID")
    private Clients clientId;

    @ManyToOne
    @JoinColumn(name = "cars_id")
    private Cars cars;

    public Cars getCars() {
        return cars;
    }

    public void setCars(Cars cars) {
        this.cars = cars;
    }

    public Rents() {}

    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "carID", insertable = false, updatable = false)
    public Cars getCar() {
        return carId;
    }
    public void setCar(Cars carId) {
        this.carId = carId;
    }

    //@ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "clientID")
    public Clients getClient() {
        return clientId;
    }

    public void setClient(Clients clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "RentDate")
    public Date getDateOfRent() {
        return dateOfRent;
    }

    public void setDateOfRent(Date dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    @Basic
    @Column(name = "ReturnDate")
    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    @Basic
    @Column(name = "Price")
    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }


    @Override
    public String toString() {
        return "Rents{" +
                "id=" + id +
                ", client= " + clientId +
                ", car= " + carId +
                ", dateOfRental=" + dateOfRent +
                ", dateOfReturn=" + dateOfReturn +
                '}';
    }
}

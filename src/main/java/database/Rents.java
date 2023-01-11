package database;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Rents")

public class Rents {
    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Basic
    @Column(name = "dateOfRent")
    private Date dateOfRent;
    @Basic
    @Column(name = "dateOfReturn")
    private Date dateOfReturn;
    @Basic
    @Column(name = "rentPrice")
    private double rentPrice;

    @ManyToOne
    @JoinColumn(name="carsId", nullable = false)
    private Cars cars;
    @ManyToOne
    @JoinColumn(name="clientsId", nullable = false)
    private Clients clients;

    public Clients getClients() {return clients;}

    public void setClients(Clients clients) {this.clients = clients;}

    public Cars getCars() {return cars;}

    public void setCars(Cars cars) {this.cars = cars;}

    public Rents() {}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Date getDateOfRent() {
        return dateOfRent;
    }
    public void setDateOfRent(Date dateOfRent) {
        this.dateOfRent = dateOfRent;
    }

    public Date getDateOfReturn() {return dateOfReturn;}
    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public double getRentPrice() {
        return rentPrice;
    }
    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rents rents = (Rents) o;
        return id == rents.id && Double.compare(rents.rentPrice, rentPrice) == 0 && dateOfRent.equals(rents.dateOfRent) && dateOfReturn.equals(rents.dateOfReturn) && cars.equals(rents.cars) && clients.equals(rents.clients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfRent, dateOfReturn, rentPrice, cars, clients);
    }

    @Override
    public String toString() {
        return "Rents{" +
                "id=" + id +
                ", client= " + clients.getId() +
                ", car= " + cars.getId() +
                ", dateOfRental=" + dateOfRent +
                ", dateOfReturn=" + dateOfReturn +
                '}';
    }

}

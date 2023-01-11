package renting;
import database.Cars;
import database.Clients;
import database.Rents;
import org.hibernate.Session;
import org.hibernate.query.Query;
import sun.util.*;
import sun.util.calendar.CalendarDate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;


public class Main {

    public static void main(String[] args) {
       /* final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();*/


            LogManager logManager = LogManager.getLogManager();
            Logger logger = logManager.getLogger("");
            logger.setLevel(Level.SEVERE); //could be Level.OFF


        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            session.beginTransaction();
            Cars cars = new Cars();
            Clients clients = new Clients();
            Rents rents = new Rents();
            Scanner scanner = new Scanner(System.in);

            System.out.println("Who are you?");
            System.out.println("1.Worker");
            System.out.println("2.Client");
            int opt1 = scanner.nextInt();
            int opt2;
            if (opt1 == 1) {
                System.out.println("Welcome to your panel, choose an action: ");
                System.out.println("1. View the list of clients");
                System.out.println("2. View the list of rents");
                System.out.println("3. Add a new client");
                System.out.println("4. Add a new car");
                System.out.println("5. Add a new rent");
                opt2 = scanner.nextInt();
                if (opt2 == 1) {
                    System.out.println("The list of clients");
                    String hql = "FROM Clients";
                    Query query = session.createQuery(hql);
                    List<Clients> list = query.list();

                    for(Clients clients1 : list){
                        System.out.println(clients1.getId() +" "+ clients1.getName()+ " "+clients1.getSurname());
                    }
                } else if (opt2 == 2){
                    System.out.println("The list of rents");
                    String hql = "FROM Rents";
                    Query query = session.createQuery(hql);
                    List<Rents> list = query.list();

                    System.out.println("ID\tID client\tID car\tDate of rent\tDate of return");
                    for(Rents rents1 : list){
                        System.out.println(rents1.getId() + " " + rents1.getClients()+ " " +
                                rents1.getCars() + " " + rents1.getDateOfRent() + rents1.getDateOfReturn());
                    }
                } else if (opt2 == 3){
                    String name, surname;
                    System.out.println("Time to add a new client: ");

                    System.out.println("Name: ");
                    name = scanner.next();
                    clients.setName(name);

                    System.out.println("Surname: ");
                    surname = scanner.next();
                    clients.setSurname(surname);

                    session.merge(clients);
                    System.out.println("Name\t Surname");
                    System.out.println("New Client: " + clients.getName()+ " " + clients.getSurname());

                } else if (opt2 == 4){
                    String brand, model;
                    double power, price;
                    System.out.println("Time to add a new car: ");

                    System.out.println("Brand: ");
                    brand = scanner.next();
                    cars.setBrand(brand);

                    System.out.println("Model: ");
                    model = scanner.next();
                    cars.setModel(model);

                    System.out.println("Power: ");
                    power = scanner.nextDouble();
                    cars.setPower(power);

                    System.out.println("Price: ");
                    price = scanner.nextDouble();
                    cars.setPrice(price);

                    session.merge(cars);
                    System.out.println("Brand\t Model\t Power\t Price");
                    System.out.println("New Car: " + cars.getBrand()+ " " + cars.getModel()+ " " + cars.getPower()+ " " + cars.getPrice());
                } else if(opt2 == 5){
                    System.out.println("Time to add a new rent: ");
                    int hours;
                    double priceR;
                    Date date = new Date();
                    Date dateAfter = date;

                    double price = 0;
                    int clientID, carID;
                    rents.setDateOfRent(date);
                    System.out.println("For how many hours: ");
                    hours = scanner.nextInt();

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.add(Calendar.HOUR_OF_DAY, hours);
                    rents.setDateOfReturn(calendar.getTime());

                    System.out.println("Car's ID: ");
                    carID = scanner.nextInt();
                    String hql = "from Cars where id =: carID";
                    Query query = session.createQuery(hql);
                    query.setParameter("carID", carID);
                    Cars cars1 = (Cars)query.getSingleResult();
                    rents.setCars(cars1);

                    System.out.println("Client's ID: ");
                    clientID = scanner.nextInt();
                    String hql2 = "from Clients where id =: clientID";
                    Query query2 = session.createQuery(hql2);
                    query2.setParameter("clientID", clientID);
                    Clients clients1 = (Clients)query2.getSingleResult();
                    rents.setClients(clients1);

                    System.out.println("Rent price: ");
                    price = cars1.getPrice();
                    price *= hours;
                    rents.setRentPrice(price);

                    session.merge(rents);
                    System.out.println("Date of rent\t Date of return\t Price");
                    System.out.println("New Rent: " + rents.getDateOfRent() + " " + rents.getDateOfReturn()+ " " + rents.getRentPrice());
                } else {
                    System.out.println("Wrong value");
                }
            } else if (opt1 == 2) {
                int idClient;
                System.out.println("Put your ID number: ");
                idClient = scanner.nextInt();
                System.out.println("Welcome! Pick what you want to do: ");
                System.out.println("1. View the list of cars");
                System.out.println("2. View your history");
                opt2 = scanner.nextInt();
                if(opt2 == 1){
                    System.out.println("The list of cars: ");
                    String hql = "FROM Cars";
                    Query query = session.createQuery(hql);
                    List<Cars> list = query.list();

                    System.out.println("Brand\t Model\t Power\t Price");
                    for(Cars cars1 : list) {
                        System.out.println(cars1.getBrand() + " " + cars1.getModel() + " " + cars1.getPower() + "" + cars1.getPrice());
                    }
                } else if (opt2 == 2){
                    System.out.println("Your history: ");
                    String hql = "from Rents R where clients.id=:idClient";

                    Query query = session.createQuery(hql);
                    query.setParameter("idClient", idClient);
                    List<Rents> list =query.list();

                    System.out.println("Date of rent\t\t  |Date of return\t\t |Price");
                    for(Rents rents1 : list) {
                        System.out.println(rents1.getDateOfRent()+ " |"+ rents1.getDateOfReturn()+ " |"+ rents1.getRentPrice());
                    }
                } else {
                    System.out.println("wrong value");
                }
            } else {
                System.out.println("Wrong value");
            }

            session.getTransaction().commit();

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

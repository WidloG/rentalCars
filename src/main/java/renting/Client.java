package renting;

import database.Cars;
import database.Clients;
import database.Rents;
import org.hibernate.query.Query;

import java.net.*;
import java.io.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class Client {
    public static void main(String[] args) throws IOException {
        // Connect to the server
        Socket socket = new Socket("localhost", 4999);
        Scanner scanner = new Scanner(System.in);

        // Read from and write to the socket as needed
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {

            System.out.println("Who are you?");
            System.out.println("1.Worker");
            System.out.println("2.Client");
            System.out.println("0.Exit");
            String opt1 = scanner.nextLine();
            String opt2;
            if (Objects.equals(opt1, "1")) {
                System.out.println("Choice: " + opt1);
                System.out.println("Welcome to your panel, choose an action: ");
                System.out.println("1. View the list of clients");
                System.out.println("2. View the list of rents");
                System.out.println("3. Add a new client");
                System.out.println("4. Add a new car");
                System.out.println("5. Add a new rent");
                System.out.println("0. Exit");
                opt2 = scanner.nextLine();
                out.println(opt2);

                if (Objects.equals(opt2, "1") || Objects.equals(opt2, "2")) {
                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);
                }else if (Objects.equals(opt2, "3")) {
                    String name, surname;
                    Clients clients = null;
                    System.out.println("Time to add a new client: ");

                    System.out.println("Name: ");
                    name = scanner.next();
                    out.println(name);

                    System.out.println("Surname: ");
                    surname = scanner.next();
                    out.println(surname);

                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);
                }else if (Objects.equals(opt2, "4")) {
                    String brand, model;
                    double power, price;
                    System.out.println("Time to add a new car: ");

                    System.out.println("Brand: ");
                    brand = scanner.next();
                    out.println(brand);

                    System.out.println("Model: ");
                    model = scanner.next();
                    out.println(model);

                    System.out.println("Power: ");
                    power = scanner.nextDouble();
                    out.println(power);

                    System.out.println("Price: ");
                    price = scanner.nextDouble();
                    out.println(price);

                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);
                }else if (Objects.equals(opt2, "5")) {
                    System.out.println("Time to add a new rent: ");
                    int hours;
                    double priceR;
                    Date date = new Date();
                    Date dateAfter = date;

                    double price = 0;
                    int clientID, carID;

                    System.out.println("For how many hours: ");
                    hours = scanner.nextInt();
                    out.println(hours);

                    System.out.println("Car's ID: ");
                    carID = scanner.nextInt();
                    out.println(carID);


                    System.out.println("Client's ID: ");
                    clientID = scanner.nextInt();
                    out.println(clientID);

                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);

                }else if(Objects.equals(opt2, "0")){
                    break;
                }else{
                System.out.println("Wrong value, try again");
                }
            } else if(Objects.equals(opt1, "2")){
                int idClient;
                System.out.println("Put your ID number: ");
                idClient = scanner.nextInt();
                System.out.println("Welcome! Pick what you want to do: ");
                System.out.println("6. View the list of cars");
                System.out.println("7. View your history");
                System.out.println("0. Exit");
                opt2 = scanner.nextLine();
                out.println(opt2);
                if(Objects.equals(opt2, "6")){
                    System.out.println("The list of cars: ");

                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);
                }else if(Objects.equals(opt2, "7")){
                    System.out.println("Your history: ");
                    out.println(idClient);

                    DataInputStream din = new DataInputStream(socket.getInputStream());
                    String response = din.readUTF();
                    System.out.println(response);
                }else if(Objects.equals(opt2, "0")){
                    break;
                }else{
                    System.out.println("Wrong value, try again");
                }

            } else if(Objects.equals(opt1, "0")){
                break;
            } else{
            System.out.println("Wrong value, try again");
            }
        }
    }
}
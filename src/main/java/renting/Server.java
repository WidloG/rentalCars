package renting;
import database.Cars;
import database.Clients;
import database.Rents;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class Server {
    public static void main(String[] args) throws IOException {

        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE); //could be Level.OFF

        // Create a server socket and bind it to a port
        ServerSocket serverSocket = new ServerSocket(4999);

        System.out.println("Server listening on port 4999");

        // Listen for incoming connections
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected");

            // Create a new thread for each incoming connection
            new Thread(() -> handleRequest(socket)).start();

        }
    }

    private static void handleRequest(Socket socket) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();


            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            String request = in.readLine();
            if (Objects.equals(request, "1")) {

                System.out.println("The list of clients");
                String hql = "FROM Clients";
                Query query = session.createQuery(hql);
                List<Clients> list = query.list();
                String response = "";
                for (Clients clients1 : list) {
                    response += clients1.getId() + " " + clients1.getName() + " " + clients1.getSurname() + "\n";

                    // System.out.println(clients1.getId() +" "+ clients1.getName()+ " "+clients1.getSurname());
                }
                dout.writeUTF(response);
                dout.flush();

            } else if (Objects.equals(request, "2")) {
                System.out.println("The list of rents");
                String hql = "FROM Rents";
                Query query = session.createQuery(hql);
                List<Rents> list = query.list();
                String response = "ID ID client  ID car  Date of rent  Date of return\n";
                for (Rents rents1 : list) {

                    response += rents1.getId() + "\t" + rents1.getClients().getId() + "\t" +
                            rents1.getCars().getId() + "\t" + rents1.getDateOfRent() + "\t" + rents1.getDateOfReturn() + "\n";
                }
                dout.writeUTF(response);
                dout.flush();

            } else if (Objects.equals(request, "3")) {
                Clients clients = new Clients();

                String name = in.readLine();
                clients.setName(name);

                String surname = in.readLine();
                clients.setSurname(surname);
                session.merge(clients);
                String response = "Name\t Surname\n";
                response += clients.getName() + " " + clients.getSurname();

                dout.writeUTF(response);
                dout.flush();
            } else if (Objects.equals(request, "4")) {
                Cars cars = new Cars();

                String brand = in.readLine();
                cars.setBrand(brand);

                String model = in.readLine();
                cars.setModel(model);

                String power = in.readLine();
                cars.setPower(Double.parseDouble(power));

                String price = in.readLine();
                cars.setPrice(Double.parseDouble(price));

                session.merge(cars);
                String response = "Brand\t Model\t Power\t Price\n";
                response += cars.getBrand() + " " + cars.getModel() + " " + cars.getPower() + " " + cars.getPrice() + "\n";

                dout.writeUTF(response);
                dout.flush();
            } else if (Objects.equals(request, "5")) {
                System.out.println("Time to add a new rent: ");
                Rents rents = new Rents();
                Date date = new Date();
                Date dateAfter = date;

                rents.setDateOfRent(date);

                String hours = in.readLine();


                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.HOUR_OF_DAY, parseInt(hours));
                rents.setDateOfReturn(calendar.getTime());

                String carID = in.readLine();
                String hql = "from Cars where id =: carID";

                Query query = session.createQuery(hql);
                query.setParameter("carID", carID);
                Cars cars1 = (Cars) query.getSingleResult();
                rents.setCars(cars1);


                String clientID = in.readLine();
                String hql2 = "from Clients where id =: clientID";
                Query query2 = session.createQuery(hql2);
                query2.setParameter("clientID", clientID);
                Clients clients1 = (Clients) query2.getSingleResult();
                rents.setClients(clients1);

                System.out.println("Rent price: ");
                double price = cars1.getPrice();
                price *= parseInt(hours);
                rents.setRentPrice(price);

                session.merge(rents);
                String response = "Date of rent\t Date of return\t Price\n";
                response += rents.getDateOfRent() + " " + rents.getDateOfReturn() + " " + rents.getRentPrice() + "\n";

                dout.writeUTF(response);
                dout.flush();
            }
            if(Objects.equals(request, "6")){
                String hql = "FROM Cars";
                Query query = session.createQuery(hql);
                List<Cars> list = query.list();
                String response = "Brand\t Model\t Power\t Price\n";

                for(Cars cars1 : list) {
                    response += cars1.getBrand() + " " + cars1.getModel() + " " + cars1.getPower() + "" + cars1.getPrice() + "\n";
                }
                dout.writeUTF(response);
                dout.flush();
            }
            if(Objects.equals(request, "7")){
                String idClient = in.readLine();
                String hql = "from Rents R where clients.id=:idClient";

                Query query = session.createQuery(hql);
                query.setParameter("idClient", idClient);
                List<Rents> list =query.list();

                String response = "Date of rent\t\t  |Date of return\t\t |Price\n";
                for(Rents rents1 : list) {
                    response += rents1.getDateOfRent()+ " |"+ rents1.getDateOfReturn()+ " |"+ rents1.getRentPrice()+"\n";
                }

                dout.writeUTF(response);
                dout.flush();
            }
            session.getTransaction().commit();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
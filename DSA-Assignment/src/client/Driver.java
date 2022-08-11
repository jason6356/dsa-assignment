package client;
import adt.*;
import entity.*;
import java.io.IOException;
import java.util.Scanner;

public class Driver {


    static Scanner scanner = new Scanner(System.in);
    private static ListI<Food> menuItems;
    private static QueueI<Order> orderQueue;

    public static void main(String[] args) throws IOException {

        init();
        boolean exit = false;

        while(!exit){

            printMenu();
            System.out.print("Enter input - ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> makeOrder();
                case 2 -> serviceOrder();
                case 3 -> displayOrderDetails();
                case 4 -> clearQueue();
                case 5 -> exit = true;
            }


        }
        saveData();
        System.out.println("Program Ends!");
    }

    public static void makeOrder(){

        Order order = new Order();
        char confirm = 'N';
        do {
            System.out.printf("Current order ID: %s", order.getOrderID());
            System.out.println("\n");
            System.out.println("Food Menu");
            System.out.println("=========================");
            for (int i = 1; i <= menuItems.getNumberOfEntries(); i++)
                System.out.printf("%d. %-20s\t%.2f\n", i, menuItems.getEntry(i).getName(), menuItems.getEntry(i).getPrice());

            System.out.println("Enter your choice");
            int choice = scanner.nextInt();

            order.addItemToOrder(menuItems.getEntry(choice));
            System.out.println("Item Successfully Added!");
            System.out.println("Would u like to add more? (Y/N) ? ");
            confirm = scanner.next().charAt(0);

        }while(confirm == 'Y');

        orderQueue.enqueue(order);
    }

    public static void serviceOrder(){

        Order currentOrder = orderQueue.getFront();

        System.out.println(currentOrder);

        char confirm = 'N';
        System.out.println("Are you sure that the food is ready to be served?");
        confirm = scanner.next().charAt(0);

        if(confirm == 'Y')
            orderQueue.dequeue();

    }

    public static void displayOrderDetails(){

        System.out.println("Enter the orderID - ");
        String orderID = scanner.next();
        boolean found = false;

        QueueI<Order> newQueue = new Queue<>();
        while(!orderQueue.isEmpty()){
            Order order = orderQueue.dequeue();

            if(orderID.equals(order.getOrderID())) {
                found = true;
                System.out.println(order);
            }


            newQueue.enqueue(order);

        }

        if(!found)
            System.out.println("Order Not Found!");

        orderQueue = newQueue;
    }

    public static void clearQueue(){

        char confirm = 'N';
        System.out.print("Are you sure to clear the queue? ");

        confirm = scanner.next().charAt(0);

        if(confirm == 'Y') {
            orderQueue.clear();
            System.out.println("Successfully cleared the queue");
        }

    }

    public static void init() throws IOException {

        initData();

    }

    public static void printMenu(){

        displayCurrentQueue();

        System.out.println("Food Catering System Menu");
        System.out.println("1. Add an order ");
        System.out.println("2. Service an order");
        System.out.println("3. Display order details");
        System.out.println("4  Clear all the order");
        System.out.println("5. Exit");

    }

    public static void displayCurrentQueue() {

        System.out.println("Current Order Queue ");
        if (orderQueue.isEmpty()) {
            return;
        }

        QueueI<Order> newQueue = new Queue<>();
        while(!orderQueue.isEmpty()){
            Order order = orderQueue.dequeue();
            System.out.println(order.getOrderID());
            newQueue.enqueue(order);
        }

        orderQueue = newQueue;

    }



    public static void initData() throws IOException {
        menuItems = Food.readDataFromFile();
        orderQueue = Order.readDataFromFile(menuItems);
    }

    public static void  saveData() throws IOException {
        Food.saveDataToFile(menuItems);
        Order.saveDataToFile(orderQueue);
    }
}
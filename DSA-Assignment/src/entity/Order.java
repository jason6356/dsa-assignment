package entity;
import adt.ListI;
import adt.List;
import adt.Queue;
import adt.QueueI;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {


    private String orderID;
    private ListI<Food> orderedItems;

    private static int orderCount = 0;


    public Order() {
        orderID = generateOrderID();
        this.orderedItems = new List<Food>();
        orderCount++;
    }

    public Order(String orderID,ListI orderedItems){
        this.orderID = orderID;
        this.orderedItems = orderedItems;
        updateOrderCount(orderID);
        orderCount++;
    }

    private void updateOrderCount(String orderID){
        Matcher matcher = Pattern.compile("\\d+|\\D+").matcher(orderID);
        while (matcher.find())
        {
            try {
                int newOrderCount = Integer.parseInt(matcher.group());
                orderCount = newOrderCount;
            }catch(NumberFormatException e){
                continue;
            }

        }
    }

    private String generateOrderID(){

        if(orderCount < 100)
            return "ORD0" + orderCount;
        else if(orderCount < 10)
            return "ORD00" + orderCount;

        return "ORD" + orderCount;
    }

    public double getTotalAmount(){

        double result = 0;

        for(int i = 1; i <= orderedItems.getNumberOfEntries(); i++)
            result += orderedItems.getEntry(i).getPrice();

        return result;
    }

    public String getOrderID() {
        return orderID;
    }

    public ListI<Food> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(ListI<Food> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void addItemToOrder(Food food){
        orderedItems.add(food);
    }

    @Override
    public String toString() {

        return String.format("Order ID : %s\nOrdered Items : %s",orderID,formatOrderItemsToString());
    }

    public String formatOrderItemsToString(){
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for(int i = 1; i <= orderedItems.getNumberOfEntries(); i++){

            Food item = orderedItems.getEntry(i);
            str.append(String.format("%20s : %3.2f\n",item.getName(),item.getPrice()));
        }


        str.append(String.format("%20s : %3.2f\n", "Total Amount", getTotalAmount()));

        return str.toString();
    }

    public String toFileFormat(){
        StringBuilder str = new StringBuilder();

        str.append(orderID+"#");
        String prefix = "";

        for(int i = 1; i <= orderedItems.getNumberOfEntries(); i++) {
            str.append(prefix + orderedItems.getEntry(i).getFoodID());
            prefix = "~";
        }

        str.append("\n");
        return str.toString();
    }

    public static QueueI<Order> readDataFromFile(ListI<Food> menuItems){

        QueueI<Order> orderQueue = new Queue<>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("DSA-Assignment/src/txts/order.txt"));
            String line;
            ListI<Food> orderedItems = new List<>();
            while((line = fileReader.readLine()) != null){

                String[] data = line.split("#");
                for(String id : data[1].split("~"))
                    orderedItems.add(((List<Food>) menuItems).searchByID(id));

                orderQueue.enqueue(new Order(data[0],orderedItems));
            }

            fileReader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return orderQueue;
    }

    public static void saveDataToFile(QueueI<Order> orderQueue){

        try {
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter("DSA-Assignment/src/txts/order.txt"));

            while(!orderQueue.isEmpty()) {
                Order order = orderQueue.dequeue();
                fileWriter.write(order.toFileFormat());
            }
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
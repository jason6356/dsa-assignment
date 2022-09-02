package entity;
import adt.ArrayList;
import adt.ListInterface;
import adt.Queue;
import adt.QueueI;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Order {


    private String orderID;
    private ListInterface<Item> orderedItems;

    private int orderedItemCount;

    private int orderedAmt = 0;

    private static int orderCount = 0;


    public Order() {
        orderID = generateOrderID();
        this.orderedItems = new ArrayList<>();
        orderCount++;
    }

    public Order(String orderID,ListInterface<Item> orderedItems){
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

    public ListInterface<Item> getOrderedItems(){ return orderedItems;}

    public void setOrderedItems(ListInterface<Item> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void addItemToOrder(Item item){


        orderedItems.add(item);


    }

    @Override
    public String toString() {

        return String.format("Order ID : %s\nOrdered Items : %s",orderID,formatOrderItemsToString());
    }

    public String formatOrderItemsToString(){
        StringBuilder str = new StringBuilder();
        str.append("\n");
        for(int i = 1; i <= orderedItems.getNumberOfEntries(); i++){

            Item item = orderedItems.getEntry(i);
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

    private static Item searchItemFromID(ListInterface<Item> itemList, String id){

        for(int i = 1; i <=itemList.getNumberOfEntries(); i++)
            if(itemList.getEntry(i).getFoodID().equals(id))
                return itemList.getEntry(i);

        return null;
    }

    public static boolean overwriteFile(QueueI<Order> q) throws IOException {
        String content = "";
        Iterator queueIter = q.iterator();

        while(queueIter.hasNext())
            content += ((Order) queueIter.next()).toFileFormat();

        Files.write(Paths.get("order.txt"), content.getBytes(StandardCharsets.UTF_8));

        return true;
    }

    public static QueueI readOrderFromFile() throws IOException {

        QueueI<Order> queue = new Queue<>();
        ListInterface<Item> menuItems = Item.readItemfromFile();

        final String fileName = "order.txt";
        Stream<String> lines = Files.lines(Paths.get(fileName));

        lines.forEach(e -> {
            String[] content = e.split("#");
            String id = content[0];
            String[] foodIDs = content[1].split("~");
            ListInterface<Item> orderedItems = new ArrayList<>();
            Arrays.stream(foodIDs).forEach(foodID -> {
                orderedItems.add(searchItemFromID(menuItems,foodID));
            });
            Order order = new Order(id,orderedItems);
            System.out.println(id);
            queue.enqueue(order);
        });
        return queue;
    }
}
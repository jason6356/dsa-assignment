package entity;
import adt.ListI;
import adt.List;

public class Order {


    private String orderID;
    private ListI<Food> orderedItems;

    private static int orderCount = 0;


    public Order() {
        orderID = generateOrderID();
        this.orderedItems = new List<Food>();
        orderCount++;
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

        for(int i = 0; i < orderedItems.getNumberOfEntries(); i++)
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
}
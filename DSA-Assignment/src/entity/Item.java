package entity;

import adt.ArrayList;
import adt.ListInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Item {

    private String foodID;
    private String name;
    private double price;

    private static int foodCount = 1;

    public static final int MAX_CAPACITY = 10;

    public Item(String name, double price) {
        this.foodID = generateFoodID();
        this.name = name;
        this.price = price;
        foodCount++;
    }

    public Item(String foodID, String name, double price){
        this.foodID = foodID;
        this.name = name;
        this.price = price;
        foodCount++;
    }

    private static String generateFoodID(){

        if(foodCount <= 10)
            return "F000" + foodCount;
        else if(foodCount <= 100)
            return "F00" + foodCount;
        else
            return "F" + foodCount;
    }

    public String getFoodID() {
        return foodID;
    }

    public static int getFoodCount() {
        return foodCount;
    }

    public static void setFoodCount(int foodCount) {
        Item.foodCount = foodCount;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

   @Override
    public boolean equals(Object obj){
        return ((Item) obj).getFoodID().equals(this.foodID);
   }

    @Override
    public String toString() {
        return String.format("Food ID : %s\nFood Name : %s\nPrice : %.2f",foodID,name,price);
    }

    public String toFileFormat(){
        return String.format("%s#%s#%.2f\n",foodID,name,price);
    }

    //Read the file from folder
    public static ListInterface<Item> readItemfromFile() throws IOException {

        ListInterface<Item> result = new ArrayList<>();

        final String fileName = "item.txt";
        Stream<String> lines = Files.lines(Paths.get(fileName));

        lines.forEach(e -> {
            String[] data = e.split("#");
            result.add(new Item(data[0],data[1],Double.parseDouble(data[2])));
        });

        return result;
    }

    public static boolean overwriteFile(ListInterface<Item> items) throws IOException {

        String content = "";

        for(int i = 1; i <= items.getNumberOfEntries(); i++)
            content += items.getEntry(i).toFileFormat();


        Files.write(Paths.get("item.txt"), content.getBytes(StandardCharsets.UTF_8));

        return true;
    }
}
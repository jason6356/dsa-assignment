package entity;

import adt.List;
import adt.ListI;

import java.io.*;

public class Food {

    private String foodID;
    private String name;
    private double price;

    private static int foodCount = 1;

    public Food(String name, double price) {
        this.foodID = generateFoodID();
        this.name = name;
        this.price = price;
        foodCount++;
    }

    public Food(String foodID, String name, double price){
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
        Food.foodCount = foodCount;
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
        return ((Food) obj).getFoodID().equals(this.foodID);
   }

    @Override
    public String toString() {
        return String.format("Food ID : %s\nFood Name : %s\nPrice : %.2f",foodID,name,price);
    }

    public String toFileFormat(){
        return String.format("%s#%s#%.2f\n",foodID,name,price);
    }

    public static void saveDataToFile(ListI<Food> foodList){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DSA-Assignment/src/txts/food.txt"));
            for(int i = 1; i <= foodList.getNumberOfEntries(); i++)
                writer.write(foodList.getEntry(i).toFileFormat());

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ListI<Food> readDataFromFile(){
        ListI<Food> foodList = new List<>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader("DSA-Assignment/src/txts/food.txt"));
            String line;
            while ((line = fileReader.readLine() )!= null) {
                String[] data = line.split("#");
                foodList.add(new Food(data[0],data[1], Double.parseDouble(data[2])));
            }

        } catch(IOException e){
            throw new RuntimeException();
        }
        return foodList;
    }
}
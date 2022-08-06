package entity;

public class Food {

    private String foodID;
    private String name;
    private double price;

    private static int foodCount = 0;

    public Food(String name, double price) {
        this.foodID = generateFoodID();
        this.name = name;
        this.price = price;
        foodCount++;
    }

    private static String generateFoodID(){

        if(foodCount <= 100)
            return "F00" + foodCount;
        else if(foodCount <= 10)
            return "F000" + foodCount;
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
}
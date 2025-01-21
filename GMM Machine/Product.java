// In this class, products are combined with their specifications to form a product that encompasses all the information.
public class Product {
    private String name;
    private int price;
    private double protein;
    private double carbohydrate;
    private double fat;

    private int calorie;

    public Product(String name, int price, double protein, double carbohydrate, double fat, int calorie) {
        this.name = name;
        this.price = price;
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.fat = fat;
        this.calorie = calorie;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbohydrate() {
        return carbohydrate;
    }

    public double getFat() {
        return fat;
    }

    public int getCalorie(){
        return calorie;
    }

    public static Product parseProduct(String line) {
        String[] parts = line.split("\\s+");

        String name;
        int price;
        double protein;
        double carbohydrate;
        double fat;
        int calorie;

        // Values are assigned depending on whether the product's name is one or two words.
        if (!isInteger(parts[1])) {
            name = parts[0] + " " + parts[1];
            price = Integer.parseInt(parts[2]);
            protein = Double.parseDouble(parts[3]);
            carbohydrate = Double.parseDouble(parts[4]);
            fat = Double.parseDouble(parts[5]);
        } else {
            name = parts[0];
            price = Integer.parseInt(parts[1]);
            protein = Double.parseDouble(parts[2]);
            carbohydrate = Double.parseDouble(parts[3]);
            fat = Double.parseDouble(parts[4]);
        }

        calorie = (int) Math.round(4*protein + 4*carbohydrate + 9*fat);

        return new Product(name, price, protein, carbohydrate, fat, calorie);
    }
    private static boolean isInteger(String part) {
        try {
            Integer.parseInt(part);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

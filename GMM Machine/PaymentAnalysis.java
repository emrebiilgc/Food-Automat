// This class finds the money entered by the customer and determines which property the customer wants to use to buy the product.
public class PaymentAnalysis {
    private String Type;
    private double Value;

    private int Cash;

    public PaymentAnalysis(String Type, int Cash, double Value) {
        this.Type = Type;
        this.Cash = Cash;
        this.Value = Value;
    }

    public String getType() {
        return Type;
    }

    public int getCash() {
        return Cash;
    }

    public double getValue() {
        return Value;
    }

    public static PaymentAnalysis parseBuying(String input) {

        String[] parts = input.split("\\s+");

        String Type = "";
        double Value = 0;
        int Cash = 0;

        // It collects the money starting from the second element.
        //When encountered with a string, it knows it's the purchase type, and the next one is considered as the purchase value.
        for (int i = 1; i < parts.length; i++) {
            if (!parts[i].matches("[a-zA-Z]+")) {
                Cash += Integer.parseInt(parts[i]);
            } else {
                Type = parts[i];
                if (i + 1 < parts.length) {
                    Value = Double.parseDouble(parts[i + 1]);
                }
                break;
            }
        }

        return new PaymentAnalysis(Type, Cash, Value);
    }
}
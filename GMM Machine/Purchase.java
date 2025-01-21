public class Purchase {
    public static void buyProduct(String[] args) {
        String[] purchaseData = FileInput.readFile(args[1], false, false);

        for (String line : purchaseData) {
            PaymentAnalysis buying = PaymentAnalysis.parseBuying(line);
            int cash = buying.getCash(); // Money entered into the machine
            String type = buying.getType(); // Purchase type
            double value = buying.getValue(); // Amount of purchase type
            FileOutput.writeToFile(args[2],"INPUT: " + line ,true,true);

            int change = cash;

            if ("NUMBER".equalsIgnoreCase(type)) {
                change = cellNumberCheck(value, cash, args);
            } else {
                change = processAttributePurchase(type, value, cash,args);
            }
            // If an error occurs, the money is refunded.
            if (change == -1) {
                FileOutput.writeToFile(args[2],"RETURN: Returning your change: " + cash + " TL" ,true,true);
            }
        }

        // After the completion of purchase transactions, the remaining products are printed.
        ProductPrinter.printProducts(GMM.getMatrix(), GMM.getProductCounts(), args);
    }

    private static int cellNumberCheck(double value, int cash, String[] args) {
        int index = (int) value;
        int row = index / 4;
        int col = index % 4;

        // Invalid index check
        if (row < 0 || col < 0 || row >= GMM.getMatrix().length || col >= GMM.getMatrix()[0].length) {
            FileOutput.writeToFile(args[2],"INFO: Number cannot be accepted. Please try again with another number." ,true,true);
            return -1;
        }

        return attemptToPurchaseByIndex(row, col, cash,args);
    }

    private static int processAttributePurchase(String type, double value, int cash, String[] args) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                Product product = GMM.getMatrix()[i][j];
                if (product != null && matchProduct(product, type, value) && GMM.getProductCounts()[i][j] > 0) {
                    if (cash < product.getPrice()) {
                        FileOutput.writeToFile(args[2],"INFO: Insufficient money, try again with more money." ,true,true);
                        return -1; // If there is not enough money, it returns without further checks.
                    }
                    int purchaseResult = attemptToPurchaseByIndex(i, j, cash,args);
                    if (purchaseResult != -1) {
                        return cash - product.getPrice(); // Successful purchase and the remaining amount of money.
                    }
                }
            }
        }
        FileOutput.writeToFile(args[2],"INFO: Product not found, your money will be returned." ,true,true);
        return -1; // If the product cannot be found, it returns -1.
    }

    private static int attemptToPurchaseByIndex(int row, int col, int cash,String[] args) {
        Product product = GMM.getMatrix()[row][col];
        if (product == null || GMM.getProductCounts()[row][col] <= 0) {
            FileOutput.writeToFile(args[2],"INFO: This slot is empty, your money will be returned." ,true,true);
            return -1;
        }
        if (cash >= product.getPrice()) {
            GMM.getProductCounts()[row][col]--;
            FileOutput.writeToFile(args[2],"PURCHASE: You have bought one " + product.getName() ,true,true);
            FileOutput.writeToFile(args[2],"RETURN: Returning your change: " + (cash - product.getPrice()) + " TL" ,true,true);
            return cash - product.getPrice(); // The remaining money is displayed upon successful purchase.
        }
        return -1;
    }

    // It checks the desired type, and if the value matches, it returns.
    private static boolean matchProduct(Product product, String type, double value) {
        switch (type.toUpperCase()) {
            case "CARB":
                return Math.abs(product.getCarbohydrate() - value) <= 5;
            case "PROTEIN":
                return Math.abs(product.getProtein() - value) <= 5;
            case "FAT":
                return Math.abs(product.getFat() - value) <= 5;
            case "CALORIE":
                return Math.abs(product.getCalorie() - value) <= 5;
            default:
                return false;
        }
    }
}

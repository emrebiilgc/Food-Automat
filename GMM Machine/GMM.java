public class GMM {
    private static final int MAX_PRODUCTS_PER_CELL = 10;

    // A 2D array representing the grid where products are placed.
    private static Product[][] matrix = new Product[6][4];

    // A 2D array tracking the count of products in each cell of the grid.
    private static int[][] productCounts = new int[6][4];

    public static int fillingMachine(String[] args) {
        String[] data = FileInput.readFile(args[0], false, false);

        // The machine is filled with products sequentially.
        for (String line : data) {
            if (line.isEmpty()) continue;

            Product product = Product.parseProduct(line);
            boolean productAdded = addProductToMachine(product);

            // If all slots are full, an error is logged.
            if (!productAdded) {
                FileOutput.writeToFile(args[2],"INFO: There is no available place to put " + product.getName(),true,true);
            }

            // If the machine is full, the loop is broken.
            if (isMachineFull() && !productAdded) {

                FileOutput.writeToFile(args[2],"INFO: The machine is full!" ,true,true);
                ProductPrinter.printProducts(matrix, productCounts,args);
                return -1;
            }
        }

        ProductPrinter.printProducts(matrix, productCounts, args);
        return 0;
    }

    private static boolean addProductToMachine(Product product) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == null || (matrix[i][j].getName().equals(product.getName()) && productCounts[i][j] < MAX_PRODUCTS_PER_CELL)) {
                    matrix[i][j] = product;
                    productCounts[i][j]++;
                    return true; // Product is added.
                }
            }
        }
        return false;
    }

    private static boolean isMachineFull() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == null || productCounts[i][j] < MAX_PRODUCTS_PER_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Product[][] getMatrix() {
        return matrix;
    }

    public static int[][] getProductCounts() {
        return productCounts;
    }
}

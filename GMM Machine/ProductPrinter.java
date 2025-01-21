// The vending machine prints in the desired format.
public class ProductPrinter {
    public static void printProducts(Product[][] matrix, int[][] productCounts,String[] args) {
        FileOutput.writeToFile(args[2],"-----Gym Meal Machine-----" ,true,true);


        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != null && productCounts[i][j] > 0) {
                    Product product = matrix[i][j];
                    int count = productCounts[i][j];
                    FileOutput.writeToFile(args[2],product.getName() + "(" + product.getCalorie() + ", " + count + ")___" ,true,false);

                } else {

                    FileOutput.writeToFile(args[2],"___(0, 0)___" ,true,false);

                }
            }
            FileOutput.writeToFile(args[2],"\n",true,false);
        }
        FileOutput.writeToFile(args[2],"----------" ,true,true);

    }
}
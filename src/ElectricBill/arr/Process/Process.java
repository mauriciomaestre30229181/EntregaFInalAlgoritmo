package ElectricBill.arr.Process;


import java.util.Scanner;
import ElectricBill.arr.validate.*;


public class Process {

    public static void iniMatrix(double[][][] matrix) {
        if (matrix != null) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    for (int k = 0; k < matrix[0][0].length; k++) {
                        matrix[i][j][k] = 0;
                    }
                }
            }
        }
    }

    public static void iniDouble(double [] doubles){
        if (doubles != null){
            for (int i = 0; i < doubles.length; i++) {
                doubles [i] = 0;
            }
        }
    }

    public static void iniAverage(double[][] average) {
        if (average != null) {
            for (int i = 0; i < average.length; i++) {
                for (int j = 0; j < average[i].length; j++) {
                    average[i][j] = 0;
                }
            }
        }
    }

    public static void initNames(String[] array) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                array[i] = "";
            }
        }
    }


    /*public static void fillArrays(double[][][] customers, String customersnames) {
    Scanner scanner = new Scanner(System.in);
    String texts = "";
        if (customers.length != 0 && customersnames.length != 0) {
            for (int x = 0; x < customers.length; x++) {
                texts = "Ingrese el nombre del propietario de la casa [" + (x+1) + "]: ";
                System.out.print(texts);
                customersnames[x] = Validations.valSubName(scanner.nextLine(),texts);
                for (int y = 0; y < customers[x].length; y++) {
                    System.out.println("Mes: " + (y + 1));
                    for (int z = 0; z < customers[x][y].length; z++) {
                        customers[x][y][z] = Validations.valDouble("Ingrese el consumo mensual: ");
                    }
                }
            }
        }
    }*/

    public static void calculateAverage(double [][][] customers, double [][] average) {
        double suma = 0;

        if (customers.length != 0 && average.length != 0) {
            for (int i = 0; i < customers.length; i++) {
                for (int j = 0; j < customers[i].length; j++) {
                    for (int k = 0; k < customers[i][j].length; k++) {
                        suma = suma + customers[i][j][k];
                    }
                }
                average [i][0] = suma;
                average[i][1] = suma / (customers[i].length);
                suma = 0;
            }
        }
    }

    /*public static double calculatePrice(double[][][] customers, double  cost) {

        double sum = 0.0;
        if (customers.length != 0) {
            double cost_per_kw = (2.17/450); //costo promedio por kilowats
            for(int x = 0; x< customers.length; x++){
                sum = 0.0;
                for(int y = 0; y < customers[0].length; y++){
                    for(int z = 0; z < customers[0][0].length; z++){
                        sum += customers[x][y][z];
                    }
                }
                
            }
            cost = sum*cost_per_kw;
        }
        return cost;
    }*/

   /* public static void hig_LowConsume(double[]cost, String[] customersnames, String[] consume) {

        int max = 0 ; int low = 0;

        if (cost.length != 0 && customersnames.length != 0 && consume.length !=0) {
            for(int x = 0; x<cost.length; x++){
                if (cost[x] > cost[max]) {
                    max = x;
                }

                if (cost[x] < cost[low]) {
                    low = x;
                }
            }
            consume [0] = customersnames[max];
            consume [1] = customersnames[low];
        }
    }*/

    public static void calculaterMunipality(double [] price, double [] finalPrice, String  customernames){
        int municipality =0;
        String texts = "";
        System.out.println("Seleccione un municipio de residencia:\n1.juanjosemora\n2.puertocabello\n3.sandiego\n4.valencia\n5.losguayos");

        texts = "Ingrese el numero del municipio del cliente";
        municipality = Validations.valMaxvalues(texts,6);

        if (price.length != 0 && finalPrice.length != 0) {
            for (int i = 0; i < price.length; i++) {
                if (municipality == 1){
                    finalPrice [i] = price[i] + price[i] * 0.07;
                }
                if (municipality == 2){
                    finalPrice [i] = price[i] + price[i] * 0.05;
                }
                if (municipality == 3){
                    finalPrice [i] = price[i] + price[i] * 0.08;
                }
                if (municipality == 4){
                    finalPrice [i] = price[i] + price[i] * 0.1;
                }
                if (municipality == 5){
                    finalPrice [i] = price[i] + price[i] * 0.03;
                }
            }
        }
    }

    public static double calculatePrice(double kw, int municipality){
        double price = 0.0;
        switch (municipality) {
            case 1:
                price = kw * 0.12;
                break;
            case 2:
                price = kw * 0.15;
                break;
            case 3:
                price = kw * 0.10;
                break;
            case 4:
                price = kw * 0.08;
                break;
            case 5:
                price = kw * 0.05;
                break;
            default:
                System.out.println("Opción no válida");
                break;
        }
        return price;
    }

    
}
package GymPersonal.repositories;

import GymPersonal.validateItem.Validate;

public class Statitics {
    private int[][][] gymMatrix;
    private int[][] revenue;
    private int[] losses;


    public Statitics(int[][][] gymMatrix,int[][] revenue, int[] losses){
        this.gymMatrix = gymMatrix;
        this.revenue = revenue;
        this.losses = losses;

        if (this.gymMatrix == null || this.revenue == null|| this.losses == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    //getters
    public int[][][] getGymMatrix() { return gymMatrix;}

    public int[][] getRevenue() { return revenue;}

    public int[] getLosses() { return losses; }

    //setters
    public void setGymMatrix(int[][][] GymMatrix) { this.gymMatrix = GymMatrix; }

    public void setRevenue(int[][] Revenue) { this.revenue = Revenue; }

    public void setLosses(int[] Losses) { this.losses = Losses; }

    public void calcRevenueLosses() {
        int sum;

        if (gymMatrix != null && revenue != null) {
            for (int i = 0; i < gymMatrix.length; i++) {
                for (int j = 0; j < gymMatrix[0].length; j++) {
                    sum=0;
                    for (int k = 0; k < gymMatrix[0][0].length; k++) {
                        sum += gymMatrix[i][j][k];
                    }
                    losses[i] += Validate.valUnsoldSpots2(gymMatrix, i, j);
                    revenue[i][j] = sum;
                    revenue[i][gymMatrix[0].length] += revenue[i][j];
                }
            }
        }
    }

    public String getPercentage(int sum){
        String text;
        double percentage = (gymMatrix[0][0].length * gymMatrix[0].length) * 0.6;
        if (sum < Math.round(percentage)) {
            text = String.format(",%s", "FRACASO\n");
        } else {
            text = String.format(",%s", "EXITO\n");
        }
        return text;
    }


}

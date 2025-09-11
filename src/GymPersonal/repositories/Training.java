package GymPersonal.repositories;

import GymPersonal.helpers.ConsultData;

import java.io.IOException;

public class Training {
    private String plan;
    private String trainingClass;
    private String schedule;

    public Training(String[][] dataBase, int index) throws IllegalArgumentException {
        plan =dataBase [index][getDataTypeIndex(dataBase, "plan")];
        trainingClass =dataBase [index][getDataTypeIndex(dataBase, "clase")];
        schedule = dataBase [index][getDataTypeIndex(dataBase, "horario")];


        if (this.plan == null || this.trainingClass == null|| this.schedule == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    public Training(String[] dataBase) throws IllegalArgumentException {
        plan =dataBase [getDataTypeIndex(dataBase,"plan")];
        trainingClass =dataBase [getDataTypeIndex(dataBase,"clase")];
        schedule = dataBase [getDataTypeIndex(dataBase, "horario")];


        if (this.plan == null || this.trainingClass == null|| this.schedule == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    public String getTrainingClass() { return this.trainingClass;}
    public String getPlan() { return this.plan;}
    public String getSchedule() {return this.schedule;}


    public void setTrainingClass(String trainingClass) { this.trainingClass = trainingClass;}
    public void setPlan(String plan) { this.plan = plan;}
    public void setSchedule(String schedule) { this.schedule = schedule;}

    private int getDataTypeIndex(String[][] bookedArray, String dataType) {
        int row =0;
        if (bookedArray == null) return -1;

        for (int column = 0; column < bookedArray[0].length; column++) {
            if(bookedArray[row][column].equalsIgnoreCase(dataType))return column;
        }

        return -1;
    }

    /*private int getDataTypeIndex(String[] bookedArray) {
        if (bookedArray!=null) {
            for (int column = 0; column < bookedArray.length; column++) {
                if (ConsultData.countStringWords(bookedArray[column]) == 2) return column;
                else if (bookedArray[column].equalsIgnoreCase("m") || bookedArray[column].equalsIgnoreCase("f")) {
                    return column;
                } else if (bookedArray[column].length() == 8 || bookedArray[column].length() == 7) {
                    return column;
                } else if (bookedArray[column].toLowerCase().contains("am") || bookedArray[column].toLowerCase().contains("pm")) {
                    return column;
                } else if (bookedArray[column].toLowerCase().startsWith("plan")) return column;
                else return column;
            }
        }
        return -1;
    }*/

    private int getDataTypeIndex(String[] bookedArray, String  dataType) {
        if (bookedArray!=null) {
            for (int column = 0; column < bookedArray.length; column++) {
                if(dataType.equalsIgnoreCase("plan"))return column;
                else if(dataType.equalsIgnoreCase("clase"))return column;
                else if(dataType.equalsIgnoreCase("horario"))return column;
            }
        }
        return -1;
    }




}

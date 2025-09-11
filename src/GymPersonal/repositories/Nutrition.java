package GymPersonal.repositories;

import GymPersonal.helpers.ConsultData;

public class Nutrition {
    private String plan;
    private String item;

    public Nutrition(String[][] dataBase, int index) throws IllegalArgumentException {
        plan =dataBase [index][getDataTypeIndex(dataBase, "plan")];
        item =dataBase [index][getDataTypeIndex(dataBase, "orden")];


        if (this.plan == null || this.item == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }
    public Nutrition(String[][] dataBase, int index, int dataType) throws IllegalArgumentException {
        if (dataType == -1) throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto (DataType not found). ");
        plan =dataBase [index][dataType];
        item =dataBase [index][dataType];


        if (this.plan == null || this.item == null ) throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
    }

    public Nutrition(String[] dataBase) throws IllegalArgumentException {
        plan = dataBase[getDataTypeIndex(dataBase,"plan")];
        item = dataBase[getDataTypeIndex(dataBase, "item")];

        if (this.plan == null || this.item == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    public String getItem() { return this.item;}
    public String getPlan() { return this.plan;}

    public void setItem(String item) { this.item = item;}
    public void setPlan(String plan) { this.plan = plan;}

    private int getDataTypeIndex(String[][] bookedArray, String dataType) {
        int row =0;
        if (bookedArray == null) return -1;

        for (int column = 0; column < bookedArray[0].length; column++) {
            if(bookedArray[row][column].equalsIgnoreCase(dataType))return column;
        }

        return -1;
    }

    private int getDataTypeIndex(String[] bookedArray, String  dataType) {
        if (bookedArray!=null) {
            for (int column = 0; column < bookedArray.length; column++) {
                if(dataType.equalsIgnoreCase("plan"))return column;
                else if(dataType.equalsIgnoreCase("item"))return column;
            }
        }
        return -1;
    }
    public static boolean contiene(String[] arreglo, String valorBuscado) {
        for (String elemento : arreglo) {
            if (elemento != null && elemento.equals(valorBuscado)) {
                return true;
            }
        }
        return false;
    }


}

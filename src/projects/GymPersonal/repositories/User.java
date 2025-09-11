package projects.src.GymPersonal.repositories;

import projects.src.GymPersonal.helpers.ConsultData;
import java.io.IOException;
import java.util.InputMismatchException;

public class User {
    private String name, id, gender;
    private String[][] database;
    private int index;
    private String[] singleDatabase;


    public User(String[][] dataBase, int index) throws IllegalArgumentException, IOException {
        this.database=dataBase;
        name =dataBase [index][getDataTypeIndex(dataBase, "nombre")];
        id =dataBase [index][getDataTypeIndex(dataBase, "cedula")];
        gender = dataBase [index][getDataTypeIndex(dataBase, "genero")];

        this.utilValSubName(name);
        this.utilValId(id, dataBase);
        this.utilValGender(gender);

        if (this.name == null || this.gender == null|| this.id == null||this.database==null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    public User(String[] singleDataBase) throws IllegalArgumentException {
        this.singleDatabase=singleDataBase;
        name = singleDataBase[getDataTypeIndex(singleDataBase, getDataType(singleDataBase))];
        id = singleDataBase[getDataTypeIndex(singleDataBase, "cedula")];
        gender = singleDataBase[getDataTypeIndex(singleDataBase, "genero")];

        this.utilValSubName(name);
        this.utilValGender(gender);

        if (this.name == null || this.gender == null|| this.id == null){
            throw new IllegalArgumentException("- Error-Instancia: Objeto incompleto. ");
        }
    }

    //getters
    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    public String getGender(){
        return this.gender;
    }

    public int getIndex(){
        return this.index;
    }

    public String[][] getDatabase(){return this.database;}



    //setters
    public void setName(String Name){
        this.utilValSubName(Name);
    }
    public void setId(String Id, String[][] dataBase) throws IOException { this.utilValId(Id, dataBase);}
    public void setGender(String Gender) { this.utilValGender(Gender);}
    public void setIndex(int Index){this.index=Index;}
    public void setSingleDatabase(String[] SingleDataBase){ this.singleDatabase= SingleDataBase;}

    //utilitarias

    private void utilValSubName(String name) throws IllegalArgumentException {

        if (name.isEmpty()){
            throw new IllegalArgumentException(" [El nombre no puede ser nulo o vacio] ");
        }

        if (!utilValString(name)){
            throw new IllegalArgumentException(" [Objecto no permite caracteres especiales o numeros]");
        } else {
            this.name = name;
        }

    }

    private boolean utilValString(String text) {
        String limit = "0123456789!#$%/()=?¡¨*[]_";

        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < limit.length(); j++) {
                if (text.charAt(i) == limit.charAt(j)){
                    return false;
                }
            }
        }
        return true;
    }

    private void utilValGender(String gender) throws IllegalArgumentException {

        if (gender == null || gender.trim().isEmpty()) {
            throw new IllegalArgumentException(" [El género no puede ser nulo o vacío] ");
        }

        String genderUpper = gender.trim().toUpperCase();

        if (!genderUpper.equals("M") && !genderUpper.equals("F")) {
            throw new IllegalArgumentException(" [El género solo puede ser 'M' o 'F'] ");
        }

        this.gender = genderUpper;
    }

    private void utilValId(String text, String[][] database) throws IOException {
        String clientId = String.valueOf(utilValInt(Integer.parseInt(text), 5000000, 32000000));
        int index = ConsultData.consultIndexByIdRec(database, clientId, 0);
        if (index == -1) {
            String errorMsg = "[Cedula no registrada en base datos]";
            throw new IllegalArgumentException(errorMsg);
        }
        this.id=clientId;

    }

    private static int utilValInt(int number, int minValue, int maxValue) throws IOException {
        while (true){
            try {
                if (number < minValue || number > maxValue){
                    String errorMsg =  "[No se permiten valores MENORES a "+ minValue + " ni MAYORES a " + maxValue + "]";
                    throw new IllegalArgumentException(errorMsg);
                } else {
                    return number;
                }
            } catch (InputMismatchException e) {
                String errorMsg =  "[No se pueden usar caracteres, palabras o letras]";
                throw new IllegalArgumentException(errorMsg);
            }
        }
    }

    private int getDataTypeIndex(String[][] bookedArray, String dataType) {
        int row =0;
        if (bookedArray == null) return -1;

        for (int column = 0; column < bookedArray[0].length; column++) {
            if(bookedArray[row][column].equalsIgnoreCase(dataType))return column;
        }

        return -1;
    }

    private int getDataTypeIndex(String[] bookedArray, String dataType) {
        if (bookedArray == null) return -1;

        for (int column = 0; column < bookedArray.length; column++) {
            if(bookedArray[column].equalsIgnoreCase(dataType))return column;
        }

        return -1;
    }

    /*private int getDataTypeIndex(String[] bookedArray, String  dataType) {
        int found=0, suma;
        if (bookedArray!=null) {
            for (int column = 0; column < bookedArray.length; column++) {
                if(dataType.equalsIgnoreCase("nombre")&&(found==0)){
                    found+=1;
                    return column;
                } else if(dataType.equalsIgnoreCase("cedula")&&(found==1)){
                    suma =column+found;
                    found++;
                    return suma;
                } else if(dataType.equalsIgnoreCase("genero")&&(found==2)){
                    suma =column+found;
                    found++;
                    return suma;
                }
            }
        }
        return -1;
    }*/

    private String getDataType(String[] bookedArray) {
        if (bookedArray!=null) {
            for (int column = 0; column < bookedArray.length; column++) {
                if (ConsultData.countStringWords(bookedArray[column]) == 2) return "nombre";
                else if (bookedArray[column].equalsIgnoreCase("m") || bookedArray[column].equalsIgnoreCase("f")) {
                    return "genero";
                } else if (bookedArray[column].length() == 8 || bookedArray[column].length() == 7) {
                    return "cedula";
                } else if (bookedArray[column].toLowerCase().contains("am") || bookedArray[column].toLowerCase().contains("pm")) {
                    return "horario";
                } else if (bookedArray[column].toLowerCase().startsWith("plan")) return "plan";
            }
        }
        return null;
    }

}
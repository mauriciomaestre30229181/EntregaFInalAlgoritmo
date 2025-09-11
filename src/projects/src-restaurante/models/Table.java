package models;

public class Table {
    private int number;
    private int personsInTable;
    private boolean isBusy;
    private Diner[] diners;

    // CONSTRUCTORS
    public Table(int number, int personsInTable, boolean isBusy) {
        setNumber(number);
        setPersonsInTable(personsInTable);
        setBusy(isBusy);
    }

    public Table(int number, int personsInTable, boolean isBusy, Diner[] diners) {
        setNumber(number);
        setPersonsInTable(personsInTable);
        setBusy(isBusy);
        setDiners(diners);
    }

    // NUMBER
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number <= 0)
            throw new IllegalArgumentException("El numero de mesa debe ser mayor a 0.");

        this.number = number;
    }

    // PERSONS IN TABLE
    public int getPersonsInTable() {
        return personsInTable;
    }

    public void setPersonsInTable(int personsInTable) {
        if (personsInTable < 0)
            throw new IllegalArgumentException("La cantidad de personas debe ser mayor o igual a 0.");

        this.personsInTable = personsInTable;
    }

    // IS BUSY
    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    // DINERS
    public Diner[] getDiners() {
        return diners;
    }

    public void setDiners(Diner[] diners) {
        if (diners == null)
            throw new IllegalArgumentException("El arreglo de comensales no puede ser nulo.");

        this.diners = diners;
    }
}
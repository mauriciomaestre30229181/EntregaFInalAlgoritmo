package models;

public class Diner {
    private String name;
    private int numberOfItems;
    private Item[] items;

    // CONSTRUCTORS
    public Diner(int numberOfItems) {
        setNumberOfItems(numberOfItems);
    }

    public Diner(String name, int numberOfItems, Item[] items) {
        setName(name);
        setNumberOfItems(numberOfItems);
        setItems(items);
    }
  
    // NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del comensal no puede ser nulo o vacío.");

        this.name = name;
    }

    // NUMBER OF ITEMS
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        if (numberOfItems < 0)
            throw new IllegalArgumentException("La cantidad de items no puede ser negativa.");

        this.numberOfItems = numberOfItems;
    }

    // ITEMS
    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        if (items == null)
            throw new IllegalArgumentException("El arreglo de items no puede ser nulo.");

        this.items = items;
    }
}
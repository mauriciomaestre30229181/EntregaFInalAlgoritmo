package models;

public class Item {
    private String name;
    private int quantity;
    private double price;

    // CONSTRUCTORS
    public Item(int quantity, double price) {
        setQuantity(quantity);
        setPrice(price);
    }

    public Item(String name, int quantity, double price) {
        setName(name);
        setQuantity(quantity);
        setPrice(price);
    }

    // NAME
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("El nombre del item no puede ser nulo o vacío.");

        this.name = name;
    }

    // QUANTITY
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor o igual a 0.");

        this.quantity = quantity;
    }

    // PRICE
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("El precio debe ser mayor o igual a 0.");

        this.price = price;
    }
}
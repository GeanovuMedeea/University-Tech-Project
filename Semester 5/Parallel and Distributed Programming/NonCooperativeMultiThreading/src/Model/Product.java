package Model;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Product {
    private String name;
    private float unitPrice;
    private int quantity;
    private int soldQuantity;
    private final ReentrantLock mutex;

    public Product(String name, float unitPrice, int quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.mutex = new ReentrantLock(true);
        this.soldQuantity = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public Lock getMutex() {
        return mutex;
    }

    public void setSoldQuantity(int soldQuantity) throws Exception {
        if(this.quantity - soldQuantity < 0)
            throw new Exception("Quantity cannot be negative!" + this.name);
        this.soldQuantity = this.soldQuantity + soldQuantity;
        this.quantity = this.quantity-soldQuantity;
    }
}

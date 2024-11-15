package Model;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Bill {
    private final int billNumber;
    private float billMoney;
    private final List<Product> billProducts;
    //private final ReentrantLock strictWritingMutex;

    public Bill(List<Product> products, int billNumber)
    {
        this.billNumber = billNumber;
        this.billProducts = products;
        //this.strictWritingMutex = new ReentrantLock(true);

    }

    public int getBillNumber(){
        return  this.billNumber;
    }

    private void computeBillMoney(){
        for(Product product: this.billProducts)
            this.billMoney = this.billMoney + product.getUnitPrice()*product.getQuantity();
    }

    public float getBillMoney()
    {
        if(this.billMoney == 0)
            computeBillMoney();
        return billMoney;
    }

    public List<Product> getProducts() {
        return billProducts;
    }

    /*public ReentrantLock getStrictWritingMutex() {
        return strictWritingMutex;
    }*/
}
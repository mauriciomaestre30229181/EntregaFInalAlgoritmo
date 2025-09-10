package main.projects.src_ProyectoButacas.objects;

public class Bill {
    private double bill;
    private String  serialNumber;

    public Bill(double bill, String serialNumber){
        this.utilValBill(bill);
        this.
    }

    public double getBill(){
        return this.bill;
    }

    public void setBill(double bill){
        this.utilValBill(bill);
    }

    private void utilValBill(double bill){
        if (bill<0){
            throw new IllegalArgumentException(" La cuenta no puede ser negativa. ");
        }else{
            this.bill=bill;
        }
    }
    
}

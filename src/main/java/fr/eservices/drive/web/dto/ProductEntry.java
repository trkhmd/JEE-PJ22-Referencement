package fr.eservices.drive.web.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductEntry {
    private String ean13;
    private int quantity;

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package fr.eservices.drive.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PerishableEntry {
    private String ean13;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date bestBefore;

    private String lot;

    private int quantity;

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }


    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

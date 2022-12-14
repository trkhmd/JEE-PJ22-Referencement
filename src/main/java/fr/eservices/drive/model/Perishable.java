package fr.eservices.drive.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Perishable extends Stock {
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date bestBefore;

    private String lot;

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
}
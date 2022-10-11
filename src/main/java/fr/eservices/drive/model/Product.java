package fr.eservices.drive.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PRODUCT")
public class Product extends Article {
}

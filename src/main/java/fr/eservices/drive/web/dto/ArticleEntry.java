package fr.eservices.drive.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ArticleEntry {
    
    @NotEmpty(message = "*Reference is required")
    @Size(min=13, max=13, message = "*Reference must be 13 characters long")
    private String ean13;
    @Min(value = 0, message = "*Price must be positive")
    private int price;
    @Min(value = 0, message = "*TVA must be at least 0") 
    @Max(value = 100, message = "*TVA must be less than 100")
    private int vat;
    @NotEmpty(message = "*Name is required")
    private String name;
    private String img;
    private List<String> categories = new ArrayList<>();

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}

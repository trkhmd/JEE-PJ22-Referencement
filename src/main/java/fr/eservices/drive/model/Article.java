package fr.eservices.drive.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Article {
	@Id
	private String ean13;
	private String name;
	private int price;
	private int vat;
	private String img;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Category> categories = new ArrayList<>();

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

	public void setVat(int tva) {
		this.vat = tva;
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

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public String toString() {
		return "Article{" +
				"ean13='" + ean13 + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", vat=" + vat +
				", img='" + img + '\'' +
				", categories=" + categories +
				'}';
	}
}
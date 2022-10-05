package fr.eservices.drive.model;

public class Article {
	
	private String
		id,
		name,
		img,
		cat_id;
	
	private int price;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
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
	
	public String getCat_id() {
		return cat_id;
	}
	
	public void setCat_id(String cat_id) {
		this.cat_id = cat_id;
	}

	
}

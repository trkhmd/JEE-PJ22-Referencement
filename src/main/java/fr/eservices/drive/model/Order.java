package fr.eservices.drive.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.eservices.drive.dao.Status;

@Entity
@Table(name="orders")
public class Order implements Serializable {
	
	@Id
	@GeneratedValue
	long id;
	
	String customerId;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date createdOn;
	
	int amount;
	
	@ElementCollection
	List<String> articles = new ArrayList<>();
	
	Status currentStatus;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	public List<String> getArticles() {
		return articles;
	}
	public void setArticles(List<String> articles) {
		this.articles = articles;
	}
	public Status getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(Status currentStatus) {
		this.currentStatus = currentStatus;
	}
	
	
}

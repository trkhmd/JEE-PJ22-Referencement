package fr.eservices.drive.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.eservices.drive.repository.OrderRepository;

@Controller
@RequestMapping(path="/order")
public class OrderController {
	
	
	// Autowire this
	OrderRepository repoOrder;
	
	@RequestMapping(path="/ofCustomer/{custId}.html")
	public String list(@PathVariable String custId, Model model) {
		
		// use repo to get orders of a customer
		// assign in model as "orders"
		// return order list view
		
		return "";
	}

}

package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.eservices.drive.dao.ArticleDao;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Cart;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@Controller
@RequestMapping(path="/cart")
public class CartController {

	@Autowired
	@Qualifier("mock")
	CartDao daoCart;

	@Autowired
	@Qualifier("mock")
	ArticleDao daoArticle;
	
	@Autowired
	OrderRepository orderRepository;
	
	@ExceptionHandler(DataException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public String dataExceptionHandler(Exception ex) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintWriter w = new PrintWriter( out );
		ex.printStackTrace(w);
		w.close();
		return 
			"ERROR"
			+ "<!--\n" + out.toString() + "\n-->";
	}
	
	@GetMapping(path="/{id}.html", produces="text/html")
	public String getCart(@PathVariable(name="id") int id, Model model) throws DataException {
		// get cart from dao
		// assign to model var "cart"
		// return view name to display content of /WEB-INF/views/_cart_header.jsp
		if(id <= 0) {
			throw new DataException("Id is 0 or negative");
		}

		Cart cart = daoCart.getCartContent(id);
		model.addAttribute("cart", cart);
		return "_cart_header";
	}

	@ResponseBody
	@PostMapping(path="/{id}/add.json",consumes="application/json")
	public SimpleResponse add(@PathVariable(name="id") int id, @RequestBody CartEntry art) {
		SimpleResponse res = new SimpleResponse();
		Cart cart = null;
		try {
			cart = daoCart.getCartContent(id);
			if(cart == null) {
				cart = new Cart();
				daoCart.store(id, cart);
			}
		} catch (DataException e) {
			res.status = Status.ERROR;
			res.message = "Id is lower or equal 0";
			return res;
		}
		Article article = daoArticle.find(art.getId());
		if(article == null || art.getQty() <= 0) {
			res.status = Status.ERROR;
			res.message = "Article id not found";
			return res;
		}
		List<Article> articleList = cart.getArticles();
		for (int i = 0; i < art.getQty(); i++)
			articleList.add(article);

		cart.setArticles(articleList);
		res.status = Status.OK;
		res.message = "";

		return res;
	}
	
	@RequestMapping("/{id}/validate.html")
	public String validateCart(@PathVariable(name="id") int id, Model model) throws DataException {
		Cart cart = cart = daoCart.getCartContent(id);
		if(cart == null) throw new DataException("Cart not found");
		Order order = new Order();
		order.setCustomerId("chuckNorris");
		order.setCreatedOn(Date.from(Instant.now()));
		order.setCurrentStatus(fr.eservices.drive.dao.Status.ORDERED);
		List<String> articles = new ArrayList<>();
		double sum = 0;
		for(Article ar: cart.getArticles()) {
			articles.add(ar.getId());
			sum += ar.getPrice();
		}
		order.setArticles(articles);
		order.setAmount((int) sum);
		orderRepository.save(order);

		return "redirect:/order/ofCustomer/chuckNorris.html";
	}
}

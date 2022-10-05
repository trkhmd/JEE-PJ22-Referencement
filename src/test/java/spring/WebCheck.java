package spring;

import static org.junit.Assert.*;
import static fr.eservices.drive.web.dto.SimpleResponse.Status.*;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import web.HtmlUtils;
import web.WebTool;

public class WebCheck {
	
	WebTool wt;
	ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void init() {
		String port = System.getProperty("usePort");
		int p = 8080;
		if ( port != null ) {
			p = Integer.parseInt(port);
		}
		wt = new WebTool("localhost:"+p, "/exo501");
	}
	
	@Test
	public void testRunTomcat() throws Exception {
		assertTrue(
			WebTool.toString(
				wt.get("/exo501.txt")
			).contains("eiV0ahng")
		);
	}
	
	@Test
	public void cartContentOne() throws Exception {
		String cart = HtmlUtils.toString( wt.get("/cart/1.html") );
		
		Assert.assertTrue( cart.contains("Boisson énergétique") );
		Assert.assertTrue( cart.contains("2,99") );
		
		Assert.assertTrue( cart.contains("Papier Cadeau") );
		Assert.assertTrue( cart.contains("1,50") );
		
		Assert.assertTrue( cart.contains("Pur jus d&#039;orange") );
		Assert.assertTrue( cart.contains("2,35") );
	}
	
	@Test
	public void cartNoContent() throws Exception {
		String cart = HtmlUtils.toString( wt.get("/cart/666.html") );
		
		Assert.assertTrue( cart.contains("Aucun article") );
	}
	
	@Test
	public void addToCart_One() throws Exception {
		CartEntry e = new CartEntry();
		e.setId("10101010");
		e.setQty(1);
		String data = mapper.writeValueAsString( e );
		String cart = HtmlUtils.toString( wt.post("/cart/1/add.json", data) );
		SimpleResponse response = mapper.readValue(cart, SimpleResponse.class);
		assertEquals(OK, response.status);
	}
	
	@Test
	public void addToCart_NoSuchProduct() throws Exception {
		CartEntry e = new CartEntry();
		e.setId("20101010");
		e.setQty(1);
		String data = mapper.writeValueAsString( e );
		
		String cart = HtmlUtils.toString( wt.post("/cart/1/add.json", data) );
		SimpleResponse response = mapper.readValue(cart, SimpleResponse.class);
		assertEquals(ERROR, response.status);
	}
	
	@Test
	public void addToCart_QtyError() throws Exception {
		CartEntry e = new CartEntry();
		e.setId("10101010");
		e.setQty(-1);
		String data = mapper.writeValueAsString( e );
		String cart = HtmlUtils.toString( wt.post("/cart/1/add.json", data) );
		SimpleResponse response = mapper.readValue(cart, SimpleResponse.class);
		assertEquals(ERROR, response.status);
	}
	
	@Test
	public void addToCart_NewCart() throws Exception {
		CartEntry e = new CartEntry();
		e.setId("10101010");
		e.setQty(1);
		String data = mapper.writeValueAsString( e );
		int cartId = (int)(Math.random() * 10000) + 1;
		String cart = HtmlUtils.toString( wt.post("/cart/"+cartId+"/add.json", data) );
		SimpleResponse response = mapper.readValue(cart, SimpleResponse.class);
		assertEquals(OK, response.status);
		
		String content = HtmlUtils.toString( wt.get("/cart/"+cartId+".html") );
		Assert.assertTrue( content.contains("Boisson énergétique") );
	}
	
	@Test
	public void validateCart() throws Exception {
		CartEntry e = new CartEntry();
		e.setId("10101010");
		e.setQty(1);
		String data = mapper.writeValueAsString( e );
		int cartId = (int)(Math.random() * 10000) + 1;
		String cart = HtmlUtils.toString( wt.post("/cart/"+cartId+"/add.json", data) );
		SimpleResponse response = mapper.readValue(cart, SimpleResponse.class);
		assertEquals(OK, response.status);
		
		String content = HtmlUtils.toString( wt.get("/cart/"+cartId+".html") );
		Assert.assertTrue( content.contains("Boisson énergétique") );
		
		InputStream is = wt.get("/cart/"+cartId+"/validate.html");
		is.close();
		assertEquals(302, wt.code());
		
		String orderList = HtmlUtils.toString( wt.get("/order/ofCustomer/chuckNorris.html") );
		assertTrue( orderList.contains("ORDERED") );
		assertTrue( orderList.contains("2,99") );
		assertTrue( orderList.contains(new SimpleDateFormat("d/M/YY").format(new Date())) );
	}

}

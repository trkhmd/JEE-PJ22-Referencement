package spring;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.mock.CartMockDao;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.web.CartController;
import fr.eservices.drive.web.dto.CartEntry;
import fr.eservices.drive.web.dto.SimpleResponse.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=CartControllerTest.class)
@Configuration
@ComponentScan(basePackageClasses={CartMockDao.class})
public class CartControllerTest {
	
	/* **************************************************
	 * Configure Test context
	 * **************************************************/
	
	@Bean
	CartController ctrl() {
		return new CartController();
	}
	
	@Bean
	OrderDao orderDao() {
		return new OrderDao() {};
	}
	
	@Bean
	OrderRepository orderRepository() {
		return (OrderRepository) Proxy.newProxyInstance(
			CartControllerTest.class.getClassLoader(), 
			new Class[]{OrderRepository.class},
			new InvocationHandler() {
				@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					if ( "hashCode".equals(method.getName()) ) return 1;
					if ( CartControllerTest.handler != null ) {
						CartControllerTest.handler.accept(method.getName(), args);
					} else {
						System.out.println("null");
					}
					return null;
				}
			}
		);
	}
	
	/* **************************************************
	 * Utils methods for test
	 * **************************************************/
	
	private CartEntry cartEntry(String id, int qty) {
		CartEntry e = new CartEntry();
		e.setId(id);
		e.setQty(qty);
		return e;
	}
	
	/* **************************************************
	 * Tests
	 * **************************************************/

	@Autowired
	CartMockDao cartDao;
	
	@Autowired
	CartController ctrl;
	
	static BiConsumer<String, Object[]> handler;
	Order saved;
	
	@Before
	public void init() {
		handler = null;
		saved = null;
	}
	
	@Test
	public void cartAdd_Ok() throws Exception {
		assertEquals( Status.OK, 
			ctrl.add(
				1, 
				cartEntry("10101010", 1)
			).status
		);
	}
	
	@Test
	public void cartAdd_NoArticle() throws Exception {
		assertEquals( Status.ERROR, 
			ctrl.add(
				1, 
				cartEntry("10101019", 1)
			).status
		);
	}
	
	@Test
	public void cartAdd_NegativeQty() throws Exception {
		assertEquals( Status.ERROR, 
			ctrl.add(
				1, 
				cartEntry("10101010", -1)
			).status
		);
	}
	
	@Test
	public void cartAdd_NewCart() throws Exception {
		assertEquals( Status.OK, 
			ctrl.add(
				2, 
				cartEntry("10101010", 1)
			).status
		);
		long cpt =
			cartDao
			.getCartContent(2)
			.getArticles()
			.stream()
			.filter( a -> a.getId().equals("10101010") )
			.count();
		assertEquals(1, cpt);
	}
	
	@Test
	public void cartGet() throws Exception {
		ExtendedModelMap m = new ExtendedModelMap();
		String view = ctrl.getCart(1, m);
		assertEquals("_cart_header", view);
		assertEquals(cartDao.getCartContent(1), m.get("cart"));
	}
	
	@Test(expected=DataException.class)
	public void validate_NoCart() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		ctrl.validateCart(99, model);
	}
	
	@Test
	public void validate_One() throws Exception {
		ExtendedModelMap model = new ExtendedModelMap();
		handler = (method, args) -> {
			if ( "save".equals(method) )
				saved = (Order) args[0]; 
		};
		ctrl.validateCart(1, model);
		assertNotNull(saved);
		assertEquals(fr.eservices.drive.dao.Status.ORDERED, saved.getCurrentStatus());
		assertEquals(684, saved.getAmount());
		List<String> articles;
		assertNotNull( articles = saved.getArticles() );
		assertTrue( articles.contains("10101010") );
		assertTrue( articles.contains("10101012") );
		assertTrue( articles.contains("10101013") );
		// assertEquals("chuckNorris", saved.getCustomerId());
		assertTrue( (System.currentTimeMillis() - saved.getCreatedOn().getTime()) < 1000 );
		assertEquals( 0, saved.getId() );
	}
	
}

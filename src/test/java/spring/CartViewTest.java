package spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import web.HtmlUtils;

public class CartViewTest {
	
	public static String content;
	
	@BeforeClass
	public static void readPage() throws Exception {
		content = HtmlUtils.toString( new FileInputStream("src/main/webapp/WEB-INF/views/_cart_header.jsp") );
	}
	
	@Test
	public void checktagLib() throws Exception {
		assertTrue( content.contains("taglib") );
		assertTrue( content.contains("http://java.sun.com/jsp/jstl/core") );
	}
	
	@Test
	public void checkIteration() {
		String tag = HtmlUtils.toNextTag(content, "c:forEach");
		assertNotNull( "You should use a forEach tag to iterate cart items", tag );
		String items = HtmlUtils.getTagAttribute(tag, "c:forEach", "items");
		assertNotNull( "You must declare items in forEach tag", items );
		assertEquals( "You should iterate on articles", "${cart.articles}", items );
		String varName = HtmlUtils.getTagAttribute(tag, "c:forEach", "var");
		String eachContent = HtmlUtils.getTagContent(tag, "c:forEach");
		assertTrue( "You should display item price", eachContent.contains("${"+varName+".price") );
		assertTrue( "You should display item name", eachContent.contains("${"+varName+".name") );
		
		String ifTag = content;
		int ifCount = 0;
		while ( (ifTag = HtmlUtils.toNextTag(ifTag, "c:if")) != null ) {
			if ( HtmlUtils.getTagAttribute(ifTag, "c:if", "test").contains("cart") )
				ifCount++;
		}
		String chooseTag = content;
		chooseTag = HtmlUtils.toNextTag(chooseTag, "c:choose");
		if (chooseTag != null) {
			chooseTag = HtmlUtils.getTagContent(chooseTag, "c:choose");
			chooseTag = HtmlUtils.toNextTag(chooseTag, "c:when");
			assertNotNull(chooseTag);
			chooseTag = HtmlUtils.toNextTag(chooseTag, "c:otherwise");
		}
		assertTrue(
			"You should test if cart is empty with an 'if' tag or 'choose' tag",
			ifCount >= 2 || chooseTag != null
		);
		assertTrue( "You should show a message when there is no item in cart", content.toLowerCase().contains("aucun article") );
	}
	
	@Test
	public void checkEncoding() {
		String contentType = HtmlUtils.getTagAttribute(content, "%@", "contentType");
		assertNotNull("You should set content type to avoid encoding problems", contentType);
		assertTrue( "Set content type to html", contentType.contains("text/html") );
		assertTrue( "Set charset", contentType.contains("charset") );
		assertTrue( "Use UTF-8", contentType.contains("UTF-8") );
	}

}

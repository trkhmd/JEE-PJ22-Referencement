package spring;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import web.HtmlUtils;

public class OrderViewTest {
	
	public static String content;
	
	@BeforeClass
	public static void readPage() throws Exception {
		content = HtmlUtils.toString( new FileInputStream("src/main/webapp/WEB-INF/views/order_list.jsp") );
	}
	
	@Test
	public void checktagLib() throws Exception {
		assertTrue( content.contains("taglib") );
		assertTrue( content.contains("http://java.sun.com/jsp/jstl/core") );
	}
	
	@Test
	public void checkIteration() {
		String tag = HtmlUtils.toNextTag(content, "c:forEach");
		assertNotNull( "You should use a forEach tag to iterate orders items", tag );
		String items = HtmlUtils.getTagAttribute(tag, "c:forEach", "items");
		assertNotNull( "You must declare items in forEach tag", items );
		assertEquals( "You should iterate on orders", "${orders}", items );
		String varName = HtmlUtils.getTagAttribute(tag, "c:forEach", "var");
		String eachContent = HtmlUtils.getTagContent(tag, "c:forEach");
		assertTrue( "You should display order Date", eachContent.contains("${"+varName+".createdOn") );
		assertTrue( "You should display order total amount", eachContent.contains("${"+varName+".amount") );
		assertTrue( "You should display order status", eachContent.contains("${"+varName+".currentStatus") );
		
		String ifTag = content;
		int ifCount = 0;
		while ( (ifTag = HtmlUtils.toNextTag(ifTag, "c:if")) != null ) {
			if ( HtmlUtils.getTagAttribute(ifTag, "c:if", "test").contains("orders") )
				ifCount++;
		}
		String chooseTag = content;
		chooseTag = HtmlUtils.toNextTag(chooseTag, "c:choose");
		if (chooseTag != null) {
			chooseTag = HtmlUtils.getTagContent(chooseTag, "c:choose");
			chooseTag = HtmlUtils.toNextTag(chooseTag, "c:when");
			assertNotNull(chooseTag);
			if (  HtmlUtils.getTagAttribute(chooseTag, "c:when", "test").contains("orders") ) 
				chooseTag = HtmlUtils.toNextTag(chooseTag, "c:otherwise");
		}
		assertTrue(
			"You should test if there is no order with an 'if' tag or 'choose' tag",
			ifCount >= 2 || chooseTag != null
		);
		
	}
}

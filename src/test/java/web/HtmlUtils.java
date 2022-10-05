package web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtils {

	
	public static void main(String[] args) {
		String content = "<title>Apache Tomcat/8.5.4</title> \n<head>";
		System.out.println( getTagContent(content, "title") );
		System.out.println( content );
	}
	
	
	public static String getTagContent(String content, String tagName) {
		int i = content.indexOf("<" + tagName);
		if ( i < 0 ) return null;
		int j = content.indexOf(">", i+1);
		if ( j < 0 ) return null;
		i = j + 1;
		content = content.substring(i);
		j = content.indexOf("</" + tagName);
		if ( j < 0 ) return null;
		return content.substring(0, j);
	}
	
	public static String toNextTag(String content, String tagName) {
		int i = content.indexOf("<" + tagName);
		if ( i == 0 ) i = content.indexOf("<" + tagName, i+1);
		if ( i < 0 ) return null;
		return content.substring(i);
	}

	public static String getTagAttribute(String content, String tagName, String attributeName) {
		int i = content.indexOf("<" + tagName);
		if ( i < 0 ) return null;
		
		int j = content.indexOf(">", i+1);
		if ( j < 0 ) return null;
		
		content = content.substring(i, j+1);
		
		Pattern p = Pattern.compile("<"+tagName+" [^>]*"+attributeName+"=['\"]?([^'\"]*)['\"]?[^>]*>");
		Matcher m = p.matcher( content );
		if ( m.matches() ) {
			String res = m.group(1);
			if ( res.startsWith("\"") ) {
				int l = res.lastIndexOf('"', 1);
				if ( l < 0 ) l = res.length();
				return res.substring(1, l);
			}
			return res;
		}
		return null;
	}
	
	public static String toString( InputStream in ) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int read = 0;
		byte[] buffer = new byte[64000];
		while ( (read = in.read(buffer)) >= 0 ) out.write(buffer, 0, read);
		in.close();
		return new String( out.toByteArray() );
	}
	

}

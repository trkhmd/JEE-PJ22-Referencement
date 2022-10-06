package spring;
import static org.junit.Assert.assertTrue;

import java.lang.annotation.Annotation;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import fr.eservices.drive.repository.OrderRepository;
import fr.eservices.drive.web.AppConfig;
import fr.eservices.drive.web.CartController;
import fr.eservices.drive.web.OrderController;

public class OrderRepositoryTest {

	@Test
	public void checkSpringDataConfig() throws Exception {
		assertTrue(
			"OrderRepository must extends CrudRepository",
			Arrays.stream(OrderRepository.class.getInterfaces())
			.anyMatch( c -> 
				c.getName().equals("org.springframework.data.repository.CrudRepository") 
			)
		);
		Class<?> LocalContainerEntityManagerFactoryBean = Class.forName("org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean");
		assertTrue(
			"AppConfig must declare an entityManagerFactory",
			Arrays.stream(AppConfig.class.getDeclaredMethods())
			.anyMatch( m -> 
					( m.getName().equals("entityManagerFactory")
					|| ( m.getAnnotation(Bean.class) != null && "entityManagerFactory".equals( m.getAnnotation(Bean.class).name() ) )
					) && m.getReturnType().isAssignableFrom( LocalContainerEntityManagerFactoryBean )
			)
		);
		Class<?> PlatformTransactionManager = Class.forName("org.springframework.transaction.PlatformTransactionManager");
		assertTrue(
			"AppConfig must declare an entityManagerFactory",
			Arrays.stream(AppConfig.class.getDeclaredMethods())
			.anyMatch( m -> 
				( m.getName().equals("transactionManager")
					|| ( m.getAnnotation(Bean.class) != null 
						&& "transactionManager".equals( m.getAnnotation(Bean.class).name() ) 
					)
				) &&  m.getReturnType().isAssignableFrom( PlatformTransactionManager )
			)
		);
		Optional<Annotation> ann = 
			Arrays.stream(AppConfig.class.getAnnotations())
			.filter( a -> {
				return "org.springframework.data.jpa.repository.config.EnableJpaRepositories"
				.equals(a.annotationType().getName());
				}
			).findAny();
		assertTrue(
			"AppConfig must set an EnableJpaRepositories Annotation",
			ann.isPresent()
		);
		Annotation annotation = ann.get();
		String[] basePackages = (String[]) annotation.annotationType()
			.getMethod("basePackages", null)
			.invoke(annotation, null);
		boolean hasPackage = false;
		if ( basePackages != null ) {
			hasPackage |= Arrays.asList(basePackages).contains("fr.eservices.drive.repository") ;
		}
		Class[] basePackageClasses = (Class[]) annotation.annotationType()
				.getMethod("basePackageClasses", null)
				.invoke(annotation, null);
		if ( basePackageClasses != null ) {
			hasPackage |= Arrays.stream(basePackageClasses)
				.map( k -> k.getPackage().getName() )
				.anyMatch( n -> "fr.eservices.drive.repository".equals(n) );
		}
		assertTrue( hasPackage );
	}
	
	@Test
	public void testInjections() {
		assertTrue( hasFieldTypeAnnotated(CartController.class, OrderRepository.class, Autowired.class) );
		assertTrue( hasFieldTypeAnnotated(OrderController.class, OrderRepository.class, Autowired.class) );
	}
	
	@Test
	public void testMethodDeclaration() throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		assertTrue( 
			"OrderRepository must declare a method to get order of a customer, ordered by creation date, more recents first",
			Arrays.stream(OrderRepository.class.getDeclaredMethods())
				.filter( m -> m.getReturnType() == List.class )
				.map( m -> m.getName() )
				.map( n -> md.digest(n.getBytes()) )
				.map( b -> String.format("%032x", new BigInteger(1, b)) )
				.anyMatch( n -> "83ead155941ca20f27c40f10d33410d0".equals(n) )
		);
	}
	
	private boolean hasFieldTypeAnnotated(Class<?> k, Class<?> type, Class<? extends Annotation> ann) {
		return Arrays.stream( k.getDeclaredFields() )
			.anyMatch( f -> f.getAnnotation( ann ) != null && f.getType() == type );
	}
}

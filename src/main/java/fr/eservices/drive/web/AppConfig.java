package fr.eservices.drive.web;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories("fr.eservices.drive.repository")
@ComponentScan(basePackages={"fr.eservices.drive.web"})
@EnableWebMvc
public class AppConfig implements WebApplicationInitializer, ApplicationListener<ApplicationContextEvent> {
	
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(AppConfig.class);
		ServletRegistration.Dynamic registration = container.addServlet("dispatcher", new DispatcherServlet(ctx));
		registration.setLoadOnStartup(1);
		registration.addMapping("*.html", "*.json");
	}

	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean emf() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPersistenceUnitName("myApp");
		return emf;
	}
	@Bean(name = "transactionManager")
	public PlatformTransactionManager txManager(EntityManagerFactory emf ) {
		JpaTransactionManager txManager = new JpaTransactionManager(emf);
		txManager.setPersistenceUnitName("myApp");
		return txManager;
	}

	@Bean
	public ViewResolver viewResolver() {
		UrlBasedViewResolver resolver = new UrlBasedViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if ( !(event instanceof ContextClosedEvent) ) return;

		EntityManagerFactory emf = event.getApplicationContext().getBean(EntityManagerFactory.class);
		emf.close();

		try {
			Driver driver = DriverManager.getDriver("jdbc:h2:./db");
			DriverManager.deregisterDriver(driver);
		} catch( SQLException e ) {
		}
	}
}

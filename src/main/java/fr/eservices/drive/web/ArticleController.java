package fr.eservices.drive.web;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import fr.eservices.drive.model.Article;
import fr.eservices.drive.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import fr.eservices.drive.repository.DataException;

@Controller
@RequestMapping(path="/articles")
public class ArticleController {

	@Autowired
	ArticleRepository dao;
	
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
	public String getArticle(@PathVariable(name="id") int id, Model model) throws DataException {
		Article article = dao.findById(id);
		model.addAttribute("article", article);
		return "ma_vue";
	}

	@GetMapping(path = "/add/{id}.html",  produces="text/html")
	public String get(@PathVariable(name="id") int id, Model model) throws DataException {
		Article ar = new Article();
		ar.setId(id);
		ar.setEan("EAN");
		ar.setImg("img");
		ar.setCategories(new ArrayList<>());
		ar.setName("MyName");
		ar.setVat(20);
		ar.setPrice(40);
		dao.save(ar);
		Article article = dao.findById(id);
		model.addAttribute("article", article);
		return "ma_vue";
	}
}

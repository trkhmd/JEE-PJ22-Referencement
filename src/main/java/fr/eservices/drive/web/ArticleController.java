package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Category;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.CategoryRepository;
import fr.eservices.drive.web.dto.ArticleEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(path="/articles")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CategoryRepository categoryRepository;

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

    @GetMapping()
    public String getAllArticles(Model model, 
        @RequestParam(name = "cat", required = false) String catFilter,
        @RequestParam(name = "name", required = false) String nameFilter,
        @RequestParam(name = "ref", required = false) String refFilter
        ){

        // generate a list of 5 categories
        for(int i=0; i<5; i++){
            Category c = new Category();
            c.setName("Category" + i);
            categoryRepository.save(c);
        }

        // generare a list of 50 article
        for(int i = 0; i < 50; i++){
            Article article = new Article();
            article.setName("Article " + i);
            article.setEan13("EAN13 " + i);
            article.setPrice(1.0 * i);
            article.setVat(0.2);
            if( i% 3 == 0)
                article.getCategories().add(categoryRepository.findById("1"));
            if( i% 3 == 1)
                article.getCategories().add(categoryRepository.findById("2"));
            if( i% 3 == 2)
                article.getCategories().add(categoryRepository.findById("3"));
            else
                article.getCategories().add(categoryRepository.findById("4"));
            articleRepository.save(article);
        }

        if( catFilter != null ){
            Category cat = categoryRepository.findById(catFilter);
            model.addAttribute("articles", articleRepository.findByCategories(cat));
        }


        // List<Article> articles = articleRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        // System.out.println("articles = " + articles);
        // model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        return "all_articles";
    }

    @GetMapping(path="/{ean13}.html", produces="text/html")
    public String getArticle(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
        String trimmedEan13 = ean13.trim();
        Article article = articleRepository.findByEan13(trimmedEan13);
        model.addAttribute("article", article);
        return "_article_info";
    }


    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody ArticleEntry artEntry) {
        SimpleResponse res = new SimpleResponse();
        if (artEntry.getName() == null ||
                artEntry.getEan13() == null ||
                artEntry.getImg() == null ||
                artEntry.getPrice() <=0 ||
                artEntry.getVat()<= 0){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad request verify entry";
            return res;
        }
        String trimmedEan13 = artEntry.getEan13().trim();
        String trimmedName = artEntry.getName().trim();
        String trimmedImg = artEntry.getImg().trim();
        if(articleRepository.findByEan13(trimmedEan13) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Ean13 is already used";
            return res;
        }

        if(trimmedName.length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Name too short";
            return res;
        }

        if(artEntry.getVat()>1){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad Vat";
            return res;
        }

        if (trimmedEan13.length() != 13){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad ean13:" + trimmedEan13;
            return res;
        }

        //TODO: check category

        Article article = new Article();
        article.setPrice(artEntry.getPrice());
        article.setName(trimmedName);
        //TODO: add categories
        //article.setCategories(artEntry.getCategories());
        article.setImg(trimmedImg);
        article.setEan13(trimmedEan13);
        article.setVat(artEntry.getVat());
        articleRepository.save(article);
        res.status = SimpleResponse.Status.OK;
        res.message = "Article Added ean13: " + trimmedEan13;
        return res;
    }

    @DeleteMapping(path = "/{ean13}")
    public SimpleResponse delete(@PathVariable String ean13) {
        String trimmedEan13 = ean13.trim();
        SimpleResponse res = new SimpleResponse();
        Article article = articleRepository.findByEan13(trimmedEan13);
        if(article == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "article id not found";
            return res;
        }
        articleRepository.delete(article);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}


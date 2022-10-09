package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.StockRepository;
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
@RequestMapping(path="/article")
public class ArticleController {

    @Autowired
    ArticleRepository<Article> articleRepository;

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
    public String getAllArticles(Model model){
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "all_articles";
    }

    @GetMapping(path="/{ean13}.html", produces="text/html")
    public String getArticle(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
        Article article = articleRepository.findByEan13(ean13);
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

        if(articleRepository.findByEan13(artEntry.getEan13()) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Ean13 is already used";
            return res;
        }

        if(artEntry.getName().length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Name too short";
            return res;
        }

        if(artEntry.getVat()>1){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad Vat";
            return res;
        }

        if (artEntry.getEan13().length() != 13){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad ean13:" + artEntry.getEan13();
            return res;
        }

        //TODO: check category

        Article article = new Article();
        article.setPrice(artEntry.getPrice());
        article.setName(artEntry.getName());
        //TODO: add categories
        //article.setCategories(artEntry.getCategories());
        article.setImg(artEntry.getImg());
        article.setEan13(artEntry.getEan13());
        article.setVat(artEntry.getVat());
        articleRepository.save(article);
        Article added = articleRepository.findByEan13(artEntry.getEan13());
        res.status = SimpleResponse.Status.OK;
        res.message = "Article Added ean13: " + artEntry.getEan13();
        return res;
    }

    @DeleteMapping(path = "/{ean13}")
    public SimpleResponse delete(@PathVariable String ean13) {
        SimpleResponse res = new SimpleResponse();
        Article article = articleRepository.findByEan13(ean13);
        if(article == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "article id not found";
            return res;
        }
        articleRepository.delete(ean13);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}


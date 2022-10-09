package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.ProductRepository;
import fr.eservices.drive.web.dto.ProductEntry;
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
@RequestMapping(path="/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ArticleRepository articleRepository;

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
    public String getAllProducts(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("product", products);
        return "all_products";
    }

    @GetMapping(path="/ean13/{ean13}.html", produces="text/html")
    public String getProductByEan13(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
        String trimmedIEan13 = ean13.trim();
        Article article = articleRepository.findByEan13(trimmedIEan13);
        Product product = productRepository.findByArticle(article);
        model.addAttribute("product", product);
        return "_product_info";
    }

    @GetMapping(path="/{id}.html", produces="text/html")
    public String getProductById(@PathVariable(name="id") String id, Model model) throws DataException {
        String trimmedId = id.trim();
        Product product = productRepository.findById(trimmedId);
        model.addAttribute("product", product);
        return "_product_info";
    }

    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody ProductEntry productEntry) {
        SimpleResponse res = new SimpleResponse();
        if (productEntry.getEan13() == null){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad request verify entry";
            return res;
        }
        String trimmedEan13 = productEntry.getEan13().trim();
        Article article = articleRepository.findByEan13(trimmedEan13);
        if(article == null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Article couldn't be find with this Ean13:"+ trimmedEan13;
            return res;
        }
        if(productEntry.getQuantity() <= 0){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Quantity cannont be lower than 1";
            return res;
        }
        if(productRepository.findByArticle(article) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "This product is already registered, cannot be added more than once. Try to modify the existing quantity";
            return res;
        }

        Product product = new Product();
        product.setArticle(article);
        product.setQuantity(productEntry.getQuantity());
        productRepository.save(product);
        res.status = SimpleResponse.Status.OK;
        res.message = "Product added";
        return res;
    }

}



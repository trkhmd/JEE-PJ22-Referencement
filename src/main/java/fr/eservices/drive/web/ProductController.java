package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.repository.ArticleRepository;
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
    ArticleRepository<Product> productRepository;

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

    @GetMapping(path="/{ean13}.html", produces="text/html")
    public String getProduct(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
        Product product = productRepository.findByEan13(ean13);
        model.addAttribute("product", product);
        return "_product_info";
    }

    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody ProductEntry productEntry) {
        SimpleResponse res = new SimpleResponse();
        if (productEntry.getName() == null ||
                productEntry.getEan13() == null ||
                productEntry.getImg() == null ||
                productEntry.getPrice() <=0 ||
                productEntry.getVat()<= 0){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad request verify entry";
            return res;
        }

        if(productRepository.findByEan13(productEntry.getEan13()) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Ean13 is already used";
            return res;
        }

        if(productEntry.getName().length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Name too short";
            return res;
        }

        if(productEntry.getVat()>1){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad Vat";
            return res;
        }

        if (productEntry.getEan13().length() != 13){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad ean13";
            return res;
        }

        //TODO: check category

        Product product = new Product();
        product.setPrice(productEntry.getPrice());
        product.setName(productEntry.getName());
        //TODO: add categories
        //article.setCategories(artEntry.getCategories());
        product.setImg(productEntry.getImg());
        product.setEan13(productEntry.getEan13());
        product.setVat(productEntry.getVat());
        productRepository.save(product);
        res.status = SimpleResponse.Status.OK;
        res.message = "Article Added";
        return res;
    }

    @DeleteMapping(path = "/{ean13}")
    public SimpleResponse delete(@PathVariable String ean13) {
        SimpleResponse res = new SimpleResponse();
        Product product = productRepository.findByEan13(ean13);
        if(product == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Product id not found";
            return res;
        }
        productRepository.delete(ean13);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}



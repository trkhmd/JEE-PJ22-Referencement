package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Stock;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.StockRepository;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.StockEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

@Controller
@RequestMapping(path="/article")
public class ArticleController {


    @Autowired
    StockRepository stockRepository;

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

    @GetMapping(path="/{id}.html", produces="text/html")
    public String getArticle(@PathVariable(name="id") String id, Model model) throws DataException {

        Article stock = articleRepository.findOne(id);
        model.addAttribute("stock", stock);
        //TODO: add /WEB-INF/views/_stock_info.jsp
        return "_stock_info";
    }

    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody StockEntry stockEntry) {
        SimpleResponse res = new SimpleResponse();
        Article article = articleRepository.findOne(stockEntry.getArticleId());

        if(article == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Article id not found";
            return res;
        }

        if(stockEntry.getQuantity() <= 0) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock quantity cannot be lower than 1";
            return res;
        }
        Stock stock = new Stock();
        stock.setStock(stockEntry.getQuantity());
        stock.setArticle(article);
        stockRepository.save(stock);

        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }

    @DeleteMapping(path = "/{id}")
    public SimpleResponse delete(@PathVariable String id) {
        SimpleResponse res = new SimpleResponse();
        Stock stock = stockRepository.findOne(id);
        if(stock == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock id not found";
            return res;
        }
        stockRepository.delete(id);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}


package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.model.Stock;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.StockRepository;
import fr.eservices.drive.web.dto.SimpleResponse;
import fr.eservices.drive.web.dto.StockEntry;
import fr.eservices.drive.web.dto.StockModifyEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping(path="/stock")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    ArticleRepository<Perishable> perishableRepository;

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

    /**
     * Get all stocks
     * @param model
     * @return Redirect to _stocks.jsp
     */
    @GetMapping
    public String getAllStock(Model model) {
        List<Stock> stocks = stockRepository.findAll();
        model.addAttribute("stocks", stocks);
        return "_stocks";
    }

    /**
     *
     * @param id Stock id
     * @param model Model
     * @return Redirect to _stock_info.jsp
     * @throws DataException
     */
    @GetMapping(path="/{id}.html", produces="text/html")
    public String getStock(@PathVariable(name="id") String id, Model model) throws DataException {
        Stock stock = stockRepository.findById(id);
        model.addAttribute("stock", stock);
        return "_stock_info";
    }

    /**
     * Add a stock entry (pro
     * @param stockEntry StockEntry
     * @return HTTP.reponse
     */
    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody StockEntry stockEntry) {
        SimpleResponse res = new SimpleResponse();
        Article article = perishableRepository.findByEan13(stockEntry.getArticleId());

        if(article == null) {
            article = productRepository.findByEan13(stockEntry.getArticleId());
        }

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

    /**
     * Modify stock quantity
     * @param id Stock id
     * @param stockEntry new quantity
     * @return HTTP.reponse
     */
    @PutMapping(path="/{id}",consumes="application/json")
    public SimpleResponse modify(@PathVariable String id, @RequestBody StockModifyEntry stockEntry) {
        SimpleResponse res = new SimpleResponse();
        Stock stock = stockRepository.findById(id);
        if(stock == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock id not found";
            return res;
        }
        if(stockEntry.getQuantity() <= 0) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock quantity cannot be lower than 1";
            return res;
        }
        stock.setStock(stockEntry.getQuantity());
        stockRepository.save(stock);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }


    /**
     *
     * @param id Stock id
     * @return HTTP.response
     */
    @DeleteMapping(path = "/{id}")
    public SimpleResponse delete(@PathVariable String id) {
        SimpleResponse res = new SimpleResponse();
        Stock stock = stockRepository.findById(id);
        if(stock == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock id not found";
            return res;
        }
        // TODO: fix problem with delete
        // stockRepository.delete(pr);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}

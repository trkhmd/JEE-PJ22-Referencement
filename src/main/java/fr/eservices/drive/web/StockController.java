package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.model.Stock;
import fr.eservices.drive.repository.PerishedRepository;
import fr.eservices.drive.repository.ProductRepository;
import fr.eservices.drive.repository.StockRepository;
import fr.eservices.drive.web.dto.SimpleResponse;
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
@RequestMapping(path="/stocks")
public class StockController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    PerishedRepository perishableRepository;

    @Autowired
    ProductRepository productRepository;

    @ExceptionHandler(DataException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String dataExceptionHandler(Exception ex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintWriter w = new PrintWriter( out );
        ex.printStackTrace(w);
        w.close();
        return "ERROR"+ "<!--\n" + out.toString() + "\n-->";
    }

    /**
     * Get all stocks
     * @param model
     * @return Redirect to _stocks.jsp
     */
    @GetMapping
    public String getAllStock(Model model) {
        List<Product> products = productRepository.findAll();
        List<Perishable> perishables = perishableRepository.findAllByOrderByBestBefore();
        model.addAttribute("products", products);
        model.addAttribute("perishables", perishables);
        return "stocks";
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
        String trimmedId = id.trim();
        Stock stock = stockRepository.findById(trimmedId);
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
        stock.setQuantity(stockEntry.getQuantity());
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
    @GetMapping(value = "/{id}.html",produces="text/html")
    public String getStock(@PathVariable String id, Model model) {
        stockRepository.findById(id);
        return "_stocks";
    }
    @ResponseBody
    @DeleteMapping(value="/{id}.json")
    public SimpleResponse deleteStock(@PathVariable String id) {
        SimpleResponse res = new SimpleResponse();
        String trimmedId = id.trim();
        Stock stock = stockRepository.findById(trimmedId);
        if(stock == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Stock id not found";
            return res;
        }
        stockRepository.delete(stock);
        res.status = SimpleResponse.Status.OK;
        res.message = "";
        return res;
    }
}

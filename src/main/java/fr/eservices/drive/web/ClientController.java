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
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path="/client")
public class ClientController {

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
    public String getAllAvailableArticles(Model model) {
        List<Product> products = productRepository.findAllByOrderByArticle();
        List<Perishable> perishables = perishableRepository.findAllByOrderByArticle();
        Date today = new Date();
        Date fiveDays = new Date(today.getTime() + (1000 * 60 * 60 * 24 * 5));
        List<Perishable> afterFiveDays = perishables.stream().filter(p -> p.getBestBefore().after(fiveDays))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        model.addAttribute("perishables", afterFiveDays);
        return "page_client";
    }

}

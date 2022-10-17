package fr.eservices.drive.web;


import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.model.Stock;
import fr.eservices.drive.repository.PerishedRepository;
import fr.eservices.drive.repository.ProductRepository;
import fr.eservices.drive.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientArticleController {
    @Autowired
    StockRepository stockRepository;

    @Autowired
    PerishedRepository perishableRepository;

    @Autowired
    ProductRepository productRepository;

    /**
     * Returns a list of items available, if perishable articles display the ones
     * which have expiration date greater than 5 days and quantity of each item.
     * @param model
     * @return list of available items
     */

    @GetMapping()
    public String getAllAvailableArticles(Model model) {

        List<Stock> res = stockRepository.findAll();
        List<Stock> stocks = new ArrayList<>();

        for(Stock stock : res){
            if(stock instanceof Perishable){

                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar dayPlusFiveDays = Calendar.getInstance();
                dayPlusFiveDays.add(Calendar.DATE, 5);
                if (((Perishable) stock).getBestBefore().after(dayPlusFiveDays.getTime()) && stock.getQuantity()>0)
                    stocks.add(stock);
                }

            if ((stock instanceof Product) && stock.getQuantity() > 0) {
                stocks.add(stock);
            }

        }
        model.addAttribute("stocks", stocks);
        return "page_client";
    }


}

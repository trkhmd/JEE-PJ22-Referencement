package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.repository.ArticleRepository;
import fr.eservices.drive.repository.PerishedRepository;
import fr.eservices.drive.web.dto.PerishableEntry;
import fr.eservices.drive.web.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(path="/perishable")
public class PerishableController {

    @Autowired
    PerishedRepository perishableRepository;

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

    @GetMapping(path="/all.html", produces="text/html")
    public String getAllProducts(Model model){
        List<Perishable> perishables = perishableRepository.findAll();
        model.addAttribute("perishables", perishables);
        return "all_perishables";
    }

    @GetMapping(path = "/perished.html", produces="text/html")
    public String getAllProductsPerished(Model model){
        List<Perishable> perishables = perishableRepository.findAll();
        List<Perishable> perishedList = new ArrayList<>();
        for(Perishable perishable : perishables) {
            if(perishable.getBestBefore().before(Date.from(Instant.now()))) {
                perishedList.add(perishable);
            }
        }
        model.addAttribute("perishables", perishedList);
        return "perished_perishables";
    }

    // @GetMapping(path="/ean13/{ean13}.html", produces="text/html")
    // public String getPerishableByArticle(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
    //     String trimmedEan13 = ean13.trim();
    //     Article article = articleRepository.findByEan13(trimmedEan13);
    //     List<Perishable> perishables = perishableRepository.findByArticle(article);
    //     model.addAttribute("perishables", perishables);
    //     return "_perishables_info";
    // }

    @GetMapping(path="/stock/{id}.html", produces="text/html")
    public String getPerishableByStockId(@PathVariable(name="id") String id, Model model) throws DataException {
        String trimmedId = id.trim();
        Perishable perishable = perishableRepository.findById(trimmedId);
        model.addAttribute("perishable", perishable);
        return "_perishable_info";
    }

    // @ResponseBody
    // @PostMapping(path="/add.json",consumes="application/json")
    // public SimpleResponse add(@RequestBody PerishableEntry perishableEntry) {
    //     SimpleResponse res = new SimpleResponse();
    //     if (perishableEntry.getEan13() == null ||
    //             perishableEntry.getBestBefore() == null ||
    //             perishableEntry.getLot() == null){
    //         res.status =  SimpleResponse.Status.ERROR;
    //         res.message = "Bad request verify entry";
    //         return res;
    //     }
    //     String trimmedEan13 = perishableEntry.getEan13().trim();
    //     String trimmedLot = perishableEntry.getLot().trim();

    //     Article article = articleRepository.findByEan13(trimmedEan13);
    //     if(article == null) {
    //         res.status =  SimpleResponse.Status.ERROR;
    //         res.message = "Article couldn't be find with this Ean13:"+ trimmedEan13;
    //         return res;
    //     }

    //     if(trimmedLot.length() <3){
    //         res.status =  SimpleResponse.Status.ERROR;
    //         res.message = "Lot is too short";
    //         return res;
    //     }

    //     if(perishableEntry.getQuantity() <= 0){
    //         res.status =  SimpleResponse.Status.ERROR;
    //         res.message = "Quantity cannont be lower than 1";
    //         return res;
    //     }

    //     // List<Perishable> perishables = perishableRepository.findByArticle(article);
    //     for(Perishable perishable: perishables) {
    //         if(perishable.getLot().equals(trimmedLot)) {
    //             res.status =  SimpleResponse.Status.ERROR;
    //             res.message = "This lot is already registered, cannot be added more than once. Try to modify the existing quantity";
    //             return res;
    //         }
    //     }

    //     Perishable perishable = new Perishable();
    //     // perishable.setArticle(article);
    //     perishable.setLot(trimmedLot);
    //     perishable.setBestBefore(perishableEntry.getBestBefore());
    //     // perishable.setQuantity(perishableEntry.getQuantity());

    //     perishableRepository.save(perishable);
    //     res.status = SimpleResponse.Status.OK;
    //     res.message = "Perishable added";
    //     return res;
    // }


}
package fr.eservices.drive.web;

import fr.eservices.drive.dao.DataException;
import fr.eservices.drive.model.Perishable;
import fr.eservices.drive.repository.ArticleRepository;
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
    ArticleRepository<Perishable> perishableRepository;

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
        List<Perishable> perishables = perishableRepository.findAll();
        model.addAttribute("perishables", perishables);
        return "all_perishables";
    }

    @GetMapping(path = "/perished")
    public String getAllProductsPerished(Model model){
        List<Perishable> perishables = perishableRepository.findAll();
        List<Perishable> perishedList = new ArrayList<>();
        for(Perishable perishable : perishables) {
            if(perishable.getBestBefore().before(Date.from(Instant.now()))) {
                perishedList.add(perishable);
            }
        }
        model.addAttribute("perishedList", perishedList);
        return "all_perished";
    }

    @GetMapping(path="/{ean13}.html", produces="text/html")
    public String getProduct(@PathVariable(name="ean13") String ean13, Model model) throws DataException {
        Perishable perishable = perishableRepository.findByEan13(ean13);
        model.addAttribute("perishable", perishable);
        return "_perishable_info";
    }

    @ResponseBody
    @PostMapping(path="/add.json",consumes="application/json")
    public SimpleResponse add(@RequestBody PerishableEntry perishableEntry) {
        SimpleResponse res = new SimpleResponse();
        if (perishableEntry.getName() == null ||
                perishableEntry.getEan13() == null ||
                perishableEntry.getImg() == null ||
                perishableEntry.getPrice() <=0 ||
                perishableEntry.getVat()<= 0 ||
                perishableEntry.getBestBefore() == null ||
                perishableEntry.getLot() == null){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad request verify entry";
            return res;
        }
        if(perishableRepository.findByEan13(perishableEntry.getEan13()) != null) {
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Ean13 is already used";
            return res;
        }

        if(perishableEntry.getName().length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Name is too short";
            return res;
        }

        if(perishableEntry.getLot().length() <3){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Lot is too short";
            return res;
        }

        if(perishableEntry.getVat()>1){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad Vat";
            return res;
        }

        if (perishableEntry.getEan13().length() != 13){
            res.status =  SimpleResponse.Status.ERROR;
            res.message = "Bad ean13";
            return res;
        }

        //TODO: check category

        Perishable perishable = new Perishable();
        perishable.setPrice(perishableEntry.getPrice());
        perishable.setName(perishableEntry.getName());
        //TODO: add categories
        //article.setCategories(artEntry.getCategories());
        perishable.setImg(perishableEntry.getImg());
        perishable.setEan13(perishableEntry.getEan13());
        perishable.setVat(perishableEntry.getVat());
        perishable.setLot(perishableEntry.getLot());

        perishable.setBestBefore(perishableEntry.getBestBefore());
        perishableRepository.save(perishable);
        res.status = SimpleResponse.Status.OK;
        res.message = "Article Added";
        return res;
    }

    @DeleteMapping(path = "/{ean13}")
    public SimpleResponse delete(@PathVariable String ean13) {
        SimpleResponse res = new SimpleResponse();
        Perishable perishable = perishableRepository.findByEan13(ean13);
        if(perishable == null) {
            res.status = SimpleResponse.Status.ERROR;
            res.message = "Product id not found";
            return res;
        }
        perishableRepository.delete(ean13);
        res.status = SimpleResponse.Status.OK;
        res.message = "";

        return res;
    }
}
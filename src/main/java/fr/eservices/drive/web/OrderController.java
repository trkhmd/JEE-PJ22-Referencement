package fr.eservices.drive.web;

import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.model.ArticleOrder;
import fr.eservices.drive.model.ArticleStatus;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.Product;
import fr.eservices.drive.repository.ProductRepository;
import fr.eservices.drive.repository.StockRepository;
import fr.eservices.drive.web.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    @Qualifier("mock")
    OrderDao orderDao;

    @Autowired
    ProductRepository productRepository;

    @GetMapping(path = "/{id}.html")
    public String getOrder(@PathVariable(name = "id") String id, Model model) {
        Order order = orderDao.findById(id);
        model.addAttribute("order", order);

        return "_order";
    }

    @ResponseBody
    @PutMapping(path = "/{id}/{articleId}.json", produces = "application/json")
    public SimpleResponse modifyOrder(@PathVariable(name = "id") String id, @PathVariable(name = "articleId") String articleId) throws ParseException {
        SimpleResponse simpleResponse = new SimpleResponse();
        Order order = orderDao.findById(id);
        if(order == null) {
            simpleResponse.message = "Order not found";
            simpleResponse.status = SimpleResponse.Status.ERROR;
            return simpleResponse;
        }

        ArticleOrder ao = null;
        for(ArticleOrder articleOrder : order.getArticlesOrder()) {
            if(articleOrder.getArticle().getEan13().equals(articleId) && !articleOrder.getArticle().isPerishable()) {
                ao = articleOrder;
                break;
            }
        }

        if(ao == null) {
            simpleResponse.message = "Article order not found or is perishable";
            simpleResponse.status = SimpleResponse.Status.ERROR;
            return simpleResponse;
        }

        if(ao.getArticleStatus().equals(ArticleStatus.DELIVERED) || ao.getArticleStatus().equals(ArticleStatus.BACK_TO_STOCK)) {
            simpleResponse.message = "Article order can't go back to stock";
            simpleResponse.status = SimpleResponse.Status.ERROR;
            return simpleResponse;
        }

        if(ao.getArticleStatus().equals(ArticleStatus.RETURNED)) {
            Date firstDate = order.getDeliveredOn();

            long diffInMillies = Math.abs(Date.from(Instant.now()).getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if(diff > 7) {
                simpleResponse.message = "Article order can't go back to stock due to too late return";
                simpleResponse.status = SimpleResponse.Status.ERROR;
                return simpleResponse;
            }
        }

        Product product = productRepository.findByArticle(ao.getArticle());
        if(product == null) {
            product = new Product();
            product.setQuantity(ao.getQuantity());
            product.setArticle(ao.getArticle());
        } else {
            product.setQuantity(product.getQuantity() + ao.getQuantity());
        }

        productRepository.save(product);
        orderDao.updateStatus(id, ao, ArticleStatus.BACK_TO_STOCK);

        simpleResponse.message = "";
        simpleResponse.status = SimpleResponse.Status.OK;

        return simpleResponse;
    }

}

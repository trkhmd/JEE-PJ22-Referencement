package fr.eservices.drive.web;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.model.ArticleOrder;
import fr.eservices.drive.model.ArticleStatus;
import fr.eservices.drive.model.Stock;
import fr.eservices.drive.repository.StockRepository;
import fr.eservices.drive.web.dto.ArticleCart;
import fr.eservices.drive.web.dto.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="/cart")
public class CartController {

    @Autowired
    StockRepository stockRepository;

    @Autowired
    @Qualifier("mock")
    CartDao cartDao;

    @Autowired
    @Qualifier("mock")
    OrderDao orderDao;

    @GetMapping( )
    public String getCart(Model model) {
        model.addAttribute("cart", cartDao.getCartContent());
        return "_cart_header";
    }

    @ResponseBody
    @PostMapping( path = "/clear.json", produces="application/json")
    public SimpleResponse clear() {
        SimpleResponse res = new SimpleResponse();
        cartDao.clear();
        res.message = "";
        res.status = SimpleResponse.Status.OK;
        return res;
    }

    @ResponseBody
    @PostMapping( path = "/add.json", consumes="application/json", produces="application/json")
    public SimpleResponse add(@RequestBody ArticleCart articleCart) {
        SimpleResponse res = new SimpleResponse();
        if(articleCart.getStockId() == null) {
            res.message = "Missing stock ID";
            res.status = SimpleResponse.Status.ERROR;
            return res;
        }
        if(articleCart.getQuantity() < 1) {
            res.message = "Quantity is lower than 0";
            res.status = SimpleResponse.Status.ERROR;
            return res;
        }

        Stock stock = stockRepository.findById(articleCart.getStockId());
        if(stock == null) {
            res.message = "Stock not found";
            res.status = SimpleResponse.Status.ERROR;
            return res;
        }
        if(articleCart.getQuantity() > stock.getQuantity()) {
            res.message = "Not enough quantity for this article";
            res.status = SimpleResponse.Status.ERROR;
            return res;
        }
        articleCart.setArticle(stock.getArticle());
        cartDao.add(articleCart);
        res.message = "";
        res.status = SimpleResponse.Status.OK;
        return res;
    }

    @ResponseBody
    @GetMapping(path = "/validate.json", produces = "application/json")
    public SimpleResponse validate() {
        SimpleResponse res = new SimpleResponse();
        if(cartDao.getCartContent().isEmpty()) {
            res.message = "Your cart is empty";
            res.status = SimpleResponse.Status.ERROR;
            return res;
        }
        List<ArticleOrder> articleOrderList = new ArrayList<>();
        for(ArticleCart articleCart : cartDao.getCartContent()) {
            Stock stock = stockRepository.findById(articleCart.getStockId());
            if(stock == null) {
                res.status = SimpleResponse.Status.ERROR;
                res.message = "Stock not found";
                return res;
            }
            if(articleCart.getQuantity() > stock.getQuantity()) {
                res.status = SimpleResponse.Status.ERROR;
                res.message = "Not enough stock for this product : " + stock.getArticle().getName();
                return res;
            }
            ArticleOrder articleOrder = new ArticleOrder();
            articleOrder.setArticle(stock.getArticle());
            articleOrder.setArticleStatus(ArticleStatus.BEING_PREPARED);
            articleOrder.setQuantity(articleCart.getQuantity());
            articleOrderList.add(articleOrder);
        }
        for(ArticleCart articleCart : cartDao.getCartContent()) {
            Stock stock = stockRepository.findById(articleCart.getStockId());
            System.out.println((stock.getQuantity() - articleCart.getQuantity()) + " quantity");
            if(stock.getQuantity() - articleCart.getQuantity() == 0) {
                stockRepository.delete(stock);
            }
            else {
                stock.setQuantity(stock.getQuantity() - articleCart.getQuantity());
                stockRepository.save(stock);
            }
        }
        String id = orderDao.createOrder(articleOrderList);
        cartDao.clear();
        res.message = "Your order n°"+ id + " has been created";
        res.status = SimpleResponse.Status.OK;
        return res;
    }
}

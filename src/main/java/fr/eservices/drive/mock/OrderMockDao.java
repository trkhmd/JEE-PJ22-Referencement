package fr.eservices.drive.mock;

import java.time.Instant;
import java.util.*;

import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.model.Article;
import fr.eservices.drive.model.ArticleOrder;
import fr.eservices.drive.model.ArticleStatus;
import fr.eservices.drive.model.Order;
import fr.eservices.drive.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
@Qualifier("mock")
public class OrderMockDao implements OrderDao {

    private HashMap<String, Order> orders = new HashMap<>();

    ArticleRepository articleRepository;

    public OrderMockDao(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository; // need to initialize articleRepository

        try {
            articleRepository.findByEan13("1278651251702");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        {
//            ArticleOrder articleOrder = new ArticleOrder();
//            articleOrder.setArticle(articleRepository.findByEan13("1278651251702"));
//            articleOrder.setArticleStatus(ArticleStatus.DELIVERED);
//            articleOrder.setQuantity(2);

//            ArticleOrder articleOrder1 = new ArticleOrder();
//            articleOrder1.setArticle(articleRepository.findByEan13("1278651251703"));
//            articleOrder1.setArticleStatus(ArticleStatus.DELIVERED);
//            articleOrder1.setQuantity(1);
//
//            List<ArticleOrder> articles = new ArrayList<>(Arrays.asList(articleOrder, articleOrder1));
//
//            Order order = new Order();
//            order.setDeliveredOn(Date.from(Instant.now()));
//            order.setArticlesOrder(articles);
//            order.setId("1");
//
//            orders.put(order.getId(), order);
//        }
    }

    @Override
    public Order findById(String id) {
        return orders.get(id);
    }
}

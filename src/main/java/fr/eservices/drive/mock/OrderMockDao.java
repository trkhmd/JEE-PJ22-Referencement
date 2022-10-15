package fr.eservices.drive.mock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import javax.persistence.criteria.CriteriaBuilder;


@Component
@Qualifier("mock")
public class OrderMockDao implements OrderDao {

    private HashMap<String, Order> orders = new HashMap<>();

    ArticleRepository articleRepository;

    public OrderMockDao(ArticleRepository articleRepository) throws ParseException {
        this.articleRepository = articleRepository;
        {
            ArticleOrder articleOrder = new ArticleOrder();
            articleOrder.setArticle(articleRepository.findByEan13("1278651251702"));
            articleOrder.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder.setQuantity(2);

            ArticleOrder articleOrder1 = new ArticleOrder();
            articleOrder1.setArticle(articleRepository.findByEan13("1278651251703"));
            articleOrder1.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder1.setQuantity(1);

            ArticleOrder articleOrder2 = new ArticleOrder();
            articleOrder2.setArticle(articleRepository.findByEan13("1278651251704"));
            articleOrder2.setArticleStatus(ArticleStatus.RETURNED);
            articleOrder2.setQuantity(4);

            ArticleOrder articleOrder3 = new ArticleOrder();
            articleOrder3.setArticle(articleRepository.findByEan13("1278651251705"));
            articleOrder3.setArticleStatus(ArticleStatus.REFUSED);
            articleOrder3.setQuantity(1);

            ArticleOrder articleOrder4 = new ArticleOrder();
            articleOrder4.setArticle(articleRepository.findByEan13("1278651251706"));
            articleOrder4.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder4.setQuantity(1);

            ArticleOrder articleOrder5 = new ArticleOrder();
            articleOrder5.setArticle(articleRepository.findByEan13("1278651251707"));
            articleOrder5.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder5.setQuantity(1);

            ArticleOrder articleOrder6 = new ArticleOrder();
            articleOrder6.setArticle(articleRepository.findByEan13("1278651251708"));
            articleOrder6.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder6.setQuantity(1);

            ArticleOrder articleOrder7 = new ArticleOrder();
            articleOrder7.setArticle(articleRepository.findByEan13("1278651251709"));
            articleOrder7.setArticleStatus(ArticleStatus.RETURNED);
            articleOrder7.setQuantity(7);

            List<ArticleOrder> articles =
                    new ArrayList<>(Arrays.asList(articleOrder, articleOrder1,
                            articleOrder2, articleOrder3,
                            articleOrder4, articleOrder5,
                            articleOrder6, articleOrder7));

            Order order = new Order();
            order.setDeliveredOn(Date.from(Instant.now()));
            order.setArticlesOrder(articles);
            order.setId("1");

            orders.put(order.getId(), order);
        }
        {
            ArticleOrder articleOrder = new ArticleOrder();
            articleOrder.setArticle(articleRepository.findByEan13("1278651251702"));
            articleOrder.setArticleStatus(ArticleStatus.DELIVERED);
            articleOrder.setQuantity(2);

            ArticleOrder articleOrder1 = new ArticleOrder();
            articleOrder1.setArticle(articleRepository.findByEan13("1278651251708"));
            articleOrder1.setArticleStatus(ArticleStatus.REFUSED);
            articleOrder1.setQuantity(1);

            ArticleOrder articleOrder2 = new ArticleOrder();
            articleOrder2.setArticle(articleRepository.findByEan13("1278651251709"));
            articleOrder2.setArticleStatus(ArticleStatus.RETURNED);
            articleOrder2.setQuantity(6);

            List<ArticleOrder> articles =
                    new ArrayList<>(Arrays.asList(articleOrder, articleOrder1,
                            articleOrder2));

            Order order = new Order();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            order.setDeliveredOn(formatter.parse("07-10-2022"));
            order.setArticlesOrder(articles);
            order.setId("2");

            orders.put(order.getId(), order);
        }
    }

    @Override
    public Order findById(String id) {
        return orders.get(id);
    }

    @Override
    public void updateStatus(String id, ArticleOrder articleOrder, ArticleStatus articleStatus) {
        Order order = orders.get(id);
        for(ArticleOrder ao : order.getArticlesOrder()) {
            if(ao.equals(articleOrder)) {
                ao.setArticleStatus(articleStatus);
            }
        }
    }
}

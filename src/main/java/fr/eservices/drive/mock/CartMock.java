package fr.eservices.drive.mock;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.web.dto.ArticleCart;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Qualifier("mock")
public class CartMock implements CartDao {
    private final HashMap<String, ArticleCart> cart = new HashMap<>();

    @Override
    public ArticleCart getById(String id) {
        return cart.get(id);
    }

    @Override
    public void add(ArticleCart articleCart) {
        if(cart.containsKey(articleCart.getStockId())) {
            ArticleCart art = cart.get(articleCart.getStockId());
            art.setQuantity(art.getQuantity() + articleCart.getQuantity());
            cart.put(articleCart.getStockId(), art);
        } else {
            cart.put(articleCart.getStockId(), articleCart);
        }
    }

    @Override
    public List<ArticleCart> getCartContent() {
        return new ArrayList<ArticleCart>(cart.values());
    }

    @Override
    public void clear() {
        cart.clear();
    }
}

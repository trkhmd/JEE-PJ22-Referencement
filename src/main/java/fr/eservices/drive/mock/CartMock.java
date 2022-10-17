package fr.eservices.drive.mock;

import fr.eservices.drive.dao.CartDao;
import fr.eservices.drive.web.dto.ArticleCart;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("mock")
public class CartMock implements CartDao {
    private final List<ArticleCart> cart = new ArrayList<>();

    @Override
    public void add(ArticleCart articleCart) {
        boolean found = false;
        for (ArticleCart art : cart) {
            if (art.getStockId().equals(articleCart.getStockId())) {
                found = true;
                art.setQuantity(art.getQuantity() + articleCart.getQuantity());
            }
        }
        if(!found) cart.add(articleCart);
    }

    @Override
    public List<ArticleCart> getCartContent() {
        return cart;
    }

    @Override
    public void clear() {
        cart.clear();
    }
}

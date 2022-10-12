package fr.eservices.drive.web;

import fr.eservices.drive.dao.OrderDao;
import fr.eservices.drive.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    @Qualifier("mock")
    OrderDao orderDao;

    @GetMapping(path = "/{id}.html")
    public String getOrder(@PathVariable(name = "id") String id, Model model) {
        Order order = orderDao.findById(id);
        model.addAttribute("order", order);

        return "_order";
    }


}

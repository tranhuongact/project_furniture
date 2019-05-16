package application.data.service;

import application.data.model.OrderProduct;
import application.data.repository.OrderProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProductService {

    private static final Logger logger = LogManager.getLogger(OrderProductService.class);

    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct findOne(int orderProductId) {
        return orderProductRepository.findOne(orderProductId);
    }
    public List<OrderProduct> getListAllOrderProducts() {
        try {
            return orderProductRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<OrderProduct> findTopFeatureProductByAmount(){
        return orderProductRepository.findTopFeatureProductByAmount();

    }

}

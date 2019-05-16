package application.controller.api;

import application.data.model.Order;
import application.data.service.OrderService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.OrderDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {
    private static final Logger logger = LogManager.getLogger(ProductApiController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/update/{orderId}")
    public BaseApiResult updateProduct(@PathVariable int orderId,
                                       @RequestBody OrderDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            Order order = orderService.findOne(orderId);


            order.setCustomerName(dto.getCustomerName());
            order.setEmail(dto.getEmail());
            order.setAddress(dto.getAddress());
            order.setPhoneNumber(dto.getPhoneNumber());
            order.setCreatedDate(new Date());
            orderService.addNewOrder(order);



            result.setSuccess(true);
            result.setMessage("Update order successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }
    @GetMapping("/detail/{orderId}")
    public BaseApiResult detailMaterial(@PathVariable int orderId){
        DataApiResult result= new DataApiResult();

        try {

            Order orderEntity = orderService.findOne(orderId);
            if(orderEntity == null) {
                result.setSuccess(false);
                result.setMessage("Can't find this order");
            } else {
                OrderDTO dto = new OrderDTO();
                dto.setId(orderEntity.getId());
                dto.setCustomerName(orderEntity.getCustomerName());
                dto.setAddress(orderEntity.getAddress());
                dto.setPhoneNumber(orderEntity.getPhoneNumber());
                dto.setEmail(orderEntity.getEmail());
                result.setSuccess(true);
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping(value = "/delete/{orderId}")
    public BaseApiResult deleteOrder (@PathVariable int orderId) {
        BaseApiResult result= new BaseApiResult();

        try {
            Order order = orderService.findOne(orderId);
            if (order == null){
                result.setSuccess(false);
                result.setMessage("Can't find this order");
            }
            else {
                orderService.deleteOrder(orderId);
                result.setSuccess(true);
                result.setMessage("Delete order successfully !");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }
}

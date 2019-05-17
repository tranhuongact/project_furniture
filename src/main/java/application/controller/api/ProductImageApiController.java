package application.controller.api;

import application.data.model.Product;
import application.data.model.ProductImage;
import application.data.service.ProductImageService;
import application.data.service.ProductService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.ProductImageDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/product-image")
public class ProductImageApiController {

    private static final Logger logger = LogManager.getLogger(ProductImageApiController.class);

    @Autowired
    private ProductImageService productImageService;

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create")
    public BaseApiResult createProductImage(@RequestBody ProductImageDTO dto) {
        DataApiResult result = new DataApiResult();

        try {
            ProductImage productImage = new ProductImage();

            productImage.setProduct(productService.findOne(dto.getProductId()));
            productImage.setLink(dto.getLink());
            productImage.setCreatedDate(new Date());
            productImageService.addNewProductImage(productImage);

            result.setData(productImage.getId());
            result.setMessage("Save product-image successfully: productImageId = " + productImage.getId());
            result.setSuccess(true);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/update/{productImageId}")
    public BaseApiResult updateProduct(@PathVariable int productImageId,
                                       @RequestBody ProductImageDTO dto) {
        BaseApiResult result = new BaseApiResult();

        try {
            ProductImage productImage = productImageService.findOne(productImageId);

            productImage.setLink(dto.getLink());
            productImage.setProduct(productService.findOne(dto.getProductId()));
            productImage.setCreatedDate(new Date());

            productImageService.addNewProductImage(productImage);
            result.setSuccess(true);
            result.setMessage("Update product-image successfully");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping(value = "/detail/{productImageId}")
    public BaseApiResult getDetailProductImage(@PathVariable int productImageId) {
        DataApiResult result= new DataApiResult();

        try {
            ProductImage productImage = productImageService.findOne(productImageId);
            if (productImage == null){
                result.setSuccess(false);
                result.setMessage("Can't find this product-image");
            }
            else {
                ProductImageDTO dto = new ProductImageDTO();
                dto.setId(productImage.getId());
                dto.setLink(productImage.getLink());
                if (productImage.getProduct() != null){
                    dto.setProductId(productImage.getProduct().getId());
                }
                dto.setCreatedDate(productImage.getCreatedDate());
                result.setSuccess(true);
                result.setMessage("Get detail product-image successfully !");
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @GetMapping(value = "/delete/{productImageId}")
    public BaseApiResult deleteProductImage (@PathVariable int productImageId) {
        BaseApiResult result= new BaseApiResult();

        try {
            ProductImage productImage = productImageService.findOne(productImageId);
            if (productImage == null){
                result.setSuccess(false);
                result.setMessage("Can't find this product-image");
            }
            else {
                productImageService.deleteProductImage(productImageId);
                result.setSuccess(true);
                result.setMessage("Delete product-image successfully !");
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

}
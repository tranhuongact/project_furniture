package application.data.service;

import application.data.model.Product;
import application.data.model.ProductImage;
import application.data.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void addNewListProducts (List<Product> listProducts) {
        try {
            productRepository.save(listProducts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void addNewProduct (Product product) {
        try {
            productRepository.save(product);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public Product findOne (int productId) {
        return productRepository.findOne(productId);
    }

    public boolean updateProduct (Product product) {
        try {
            productRepository.save(product);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    public List<Product> getListAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public long getTotalProducts(){
        return productRepository.getTotalProducts();
    }

    public double getMaxPriceProduct(){
        return productRepository.getMaxPriceProduct();
    }

    public Page<Product> getListProductByCategoryOrProductNameContaining(Pageable pageable, Integer categoryId, String productName){
        return productRepository.getListProductByCategoryOrProductNameContaining(pageable,categoryId,productName);
    }

    public Page<Product> getListProductByPrice(Pageable pageable, Integer priceLower, Integer priceUpper){
        return productRepository.getListProductByPrice(pageable,priceLower,priceUpper);
    }

    public List<Product> getListProductByCategory(Integer categoryId){
        return productRepository.getListProductByCategory(categoryId);
    }
}

package application.controller.api;

import application.data.model.Brand;
import application.data.service.BrandService;
import application.model.api.BaseApiResult;
import application.model.api.DataApiResult;
import application.model.dto.BrandDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/api/brand")
public class BrandApiController {

    private static final Logger logger = LogManager.getLogger(BrandApiController.class);

    @Autowired
    private BrandService brandService;

    @PostMapping("/create")
    public BaseApiResult createBrand(@RequestBody BrandDTO dto){
        BaseApiResult result = new BaseApiResult();

        try {
            Brand brand = new Brand();
            brand.setName(dto.getName());
            brand.setAddress(dto.getAddress());
            brand.setEmail(dto.getEmail());
            brand.setPhoneNumber(dto.getPhoneNumber());
            brand.setCreatedDate(new Date());

            brandService.addNewBrand(brand);

            result.setSuccess(true);
            result.setMessage("Create brand successfully: " + brand.getId());
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

    @GetMapping("/detail/{brandId}")
    public DataApiResult getDetailBrand(@PathVariable int brandId){
        DataApiResult result= new DataApiResult();

        try {
            Brand brand = brandService.findOne(brandId);
            if(brand == null) {
                result.setSuccess(false);
                result.setMessage("Can't find this brand");
            } else {
                BrandDTO dto = new BrandDTO();
                dto.setId(brand.getId());
                dto.setName(brand.getName());
                dto.setEmail(brand.getEmail());
                dto.setAddress(brand.getAddress());
                dto.setPhoneNumber(brand.getPhoneNumber());
                dto.setCreatedDate(brand.getCreatedDate());

                result.setSuccess(true);
                result.setMessage("Get detail brand successfully !");
                result.setData(dto);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }

        return result;
    }

    @PostMapping("/update/{brandId}")
    public  BaseApiResult updateBrand(@PathVariable int brandId,
                                      @RequestBody BrandDTO dto){
        BaseApiResult result = new BaseApiResult();

        try {
            Brand brand = new Brand();
            brand.setName(dto.getName());
            brand.setAddress(dto.getAddress());
            brand.setEmail(dto.getEmail());
            brand.setPhoneNumber(dto.getPhoneNumber());
            brand.setCreatedDate(new Date());
            brandService.addNewBrand(brand);

            result.setSuccess(true);
            result.setMessage("Update brand " + brand.getId() + " successfully:");
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage(e.getMessage());
            logger.error(e.getMessage());
        }
        return result;
    }

}

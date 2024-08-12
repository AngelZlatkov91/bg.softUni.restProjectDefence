package bg.softuni.exam.healthNutrition.Rest.service;


import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Long addProduct(ProductCreateDTO productCreateDTO);

    List<ProductDetailsDTO> getAllProducts();
    List<ProductDetailsDTO> getProductsByKey(String searchKey);

    Optional<ProductDetailsDTO> getProductDetails(Long id);

    void deleteProduct(Long id);


}

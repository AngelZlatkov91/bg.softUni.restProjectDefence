package bg.softuni.exam.healthNutrition.Rest.service;


import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductEditPrice;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDetailsDTO addProduct(ProductCreateDTO productCreateDTO);

    List<ProductDetailsDTO> getAllProducts();

    Optional<ProductDetailsDTO> getProductDetails(String name);

    void deleteProduct(String name);


    Optional<ProductDetailsDTO> editPrice(String name, ProductEditPrice productEditPrice);

}

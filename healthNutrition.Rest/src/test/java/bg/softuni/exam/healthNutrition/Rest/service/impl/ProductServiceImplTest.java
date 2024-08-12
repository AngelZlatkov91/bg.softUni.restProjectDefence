package bg.softuni.exam.healthNutrition.Rest.service.impl;

import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import bg.softuni.exam.healthNutrition.Rest.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp(){
        this.productRepository.deleteAll();
    }
    @AfterEach
    void cleanUp(){
        productRepository.deleteAll();
    }

    @Test
    public void testAddProductCorrect(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        Optional<Product> byId = productRepository.findById(productDetailsDTO.getId());
        assertTrue(byId.isPresent());
    }
    @Test
    public void testAddProductWithExistName(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        assertThrows(IllegalArgumentException.class,() ->{
            productService.addProduct(productCreateDTO());
        });
    }

    @Test
    public void testGetAllProducts(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        List<ProductDetailsDTO> allProducts = productService.getAllProducts();
        assertEquals(1,allProducts.size());
    }

    @Test
    public void testGetProductById(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        Optional<ProductDetailsDTO> productDetails = productService.getProductDetails(productDetailsDTO.getId());
        assertEquals(productDetails.get().getName(),productCreateDTO().getName());
    }

    @Test
    public void testDeleteProduct(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        productService.deleteProduct(productDetailsDTO.getId());
        assertEquals(0,productRepository.count());
    }


    private ProductCreateDTO productCreateDTO (){
        ProductCreateDTO productCreateDTO = new ProductCreateDTO();
        productCreateDTO.setName("ISOLATE");
        productCreateDTO.setDescription("TEST ");
        productCreateDTO.setImageUrl("test image");
        productCreateDTO.setBrand("Test");
        productCreateDTO.setType("Test");
        productCreateDTO.setPrice(50.00);
        return productCreateDTO;
    }


}
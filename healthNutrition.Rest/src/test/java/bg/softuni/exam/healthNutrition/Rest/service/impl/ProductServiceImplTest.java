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
        Long id = productService.addProduct(productCreateDTO());
        Optional<Product> byId = productRepository.findById(id);
        assertTrue(byId.isPresent());
    }
    @Test
    public void testAddProductWithExistName(){
        Long id = productService.addProduct(productCreateDTO());
        assertThrows(IllegalArgumentException.class,() ->{
            productService.addProduct(productCreateDTO());
        });
    }

    @Test
    public void testGetAllProducts(){
        Long id = productService.addProduct(productCreateDTO());
        List<ProductDetailsDTO> allProducts = productService.getAllProducts();
        assertEquals(1,allProducts.size());
    }

    @Test
    public void testGetProductById(){
        Long id = productService.addProduct(productCreateDTO());
        Optional<ProductDetailsDTO> productDetails = productService.getProductDetails(id);
        assertEquals(productDetails.get().getName(),productCreateDTO().getName());
    }

    @Test
    public void testDeleteProduct(){
        Long id = productService.addProduct(productCreateDTO());
        productService.deleteProduct(id);
        assertEquals(0,productRepository.count());
    }

    @Test
    public void testGetProductBySearchKey(){
        Long id = productService.addProduct(productCreateDTO());
        List<ProductDetailsDTO> isolate = productService.getProductsByKey("isolate");
        assertEquals(1,isolate.size());
        assertEquals(id,isolate.get(0).getId());
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
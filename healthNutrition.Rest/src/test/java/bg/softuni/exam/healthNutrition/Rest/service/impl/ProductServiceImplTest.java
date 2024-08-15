package bg.softuni.exam.healthNutrition.Rest.service.impl;

import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductEditPrice;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductInCartDTO;
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
    public void testGetProductByName(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        Optional<ProductDetailsDTO> productDetails = productService.getProductDetails(productDetailsDTO.getName());
        assertEquals(productDetails.get().getName(),productCreateDTO().getName());
    }
    @Test
    public void testGetProductByName_notExist(){
        assertThrows(IllegalArgumentException.class,() ->{
            productService.getProductDetails(productCreateDTO().getName());
        });
    }

    @Test
    public void testDeleteProduct(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        productService.deleteProduct(productDetailsDTO.getName());
        assertEquals(0,productRepository.count());
    }
    @Test
    public void testDeleteNotExistProduct(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        productService.deleteProduct("Protein");
        assertEquals(1,productRepository.count());
    }

    @Test
    public  void testUpdatePriceForProduct(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        ProductEditPrice productEditPrice = new ProductEditPrice();
        productEditPrice.setPrice(55.00);
        productService.editPrice("ISOLATE",productEditPrice);
        Optional<ProductDetailsDTO> productDetails = productService.getProductDetails("ISOLATE");
        assertEquals(55.00,productDetails.get().getPrice());
    }
    @Test
    public void testUpdatePriceNotExistProduct(){
        ProductEditPrice productEditPrice = new ProductEditPrice();
        productEditPrice.setPrice(55.00);
        assertThrows(IllegalArgumentException.class,() ->{
            productService.editPrice("Protein",productEditPrice);
        });
    }

    @Test
    public void testGetProductForCart(){
        ProductDetailsDTO productDetailsDTO = productService.addProduct(productCreateDTO());
        ProductInCartDTO product = productService.productInCart("ISOLATE");
        assertEquals(product.getName(),productDetailsDTO.getName());
        assertEquals(1,product.getQuantity());
    }
    @Test
    public void testAddProductToCart_NotExistProduct(){
        assertThrows(IllegalArgumentException.class,() ->{
            productService.productInCart("test");
        });
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
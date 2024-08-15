package bg.softuni.exam.healthNutrition.Rest.web;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import bg.softuni.exam.healthNutrition.Rest.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
class RestControllerGetProductsTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    public void tearDown(){
        productRepository.deleteAll();
    }
    @Test
    public void testGetByName() throws Exception {

        // Arrange
        var actualEntity = createProduct();

        // ACT
        ResultActions result = mockMvc
                .perform(get("/api/products/get/{name}", actualEntity.getName())
                        .contentType(MediaType.APPLICATION_JSON));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(actualEntity.getName())))
                .andExpect(jsonPath("$.description", is(actualEntity.getDescription())))
                .andExpect(jsonPath("$.brand", is(actualEntity.getBrand())))
                .andExpect(jsonPath("$.price", is(actualEntity.getPrice())));
    }

    @Test
    public void testProductNotFound() throws Exception {
        mockMvc
                .perform(get("/api/products/get/{id}", "1000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testCreateProduct() throws Exception {
        // Arrange


        MvcResult result = mockMvc.perform(post("/api/products/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                 {
                    "name" : "MACA6",
                    "description" : "Test description",
                    "price" : 50.00,
                    "imageUrl" : "https://nowfoods.bg/image/catalog/4721_mainimage_1.jpg" ,
                    "type" : "PROTEIN",
                    "brand" : "AMIX"
                 }
                """)
                ).andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andReturn();
        Optional<Product> byUuid = productRepository.findByName("MACA6");

        Assertions.assertEquals("Test description", byUuid.get().getDescription());
        Assertions.assertEquals(50.00, byUuid.get().getPrice());
        Assertions.assertEquals("MACA6", byUuid.get().getName());

    }
    @Test
    public void testGetAllProducts() throws Exception {
        var actualEntity = createProduct();

        // ACT
        mockMvc
                .perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == '" + actualEntity.getName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.description == '" + actualEntity.getDescription() + "')]").exists())
                .andExpect(jsonPath("$[?(@.price == '" + actualEntity.getPrice() + "')]").exists())
                .andExpect(jsonPath("$[?(@.type == '" + actualEntity.getType() + "')]").exists())
                .andExpect(jsonPath("$[?(@.brand == '" + actualEntity.getBrand() + "')]").exists());


    }
    @Test
    public void testGetProductEmpty() throws Exception {
        mockMvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    public void testGetProductBySearchKeyNotExist() throws Exception {
        var actualEntity = createProduct();
        mockMvc.perform(get("/api/products/search/{searchKey}","test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    public void testGetProductBySearchKeyBrand() throws Exception {
        var actualEntity = createProduct();
        mockMvc.perform(get("/api/products/search/{searchKey}",actualEntity.getBrand())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == '" + actualEntity.getName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.description == '" + actualEntity.getDescription() + "')]").exists())
                .andExpect(jsonPath("$[?(@.price == '" + actualEntity.getPrice() + "')]").exists())
                .andExpect(jsonPath("$[?(@.type == '" + actualEntity.getType() + "')]").exists())
                .andExpect(jsonPath("$[?(@.brand == '" + actualEntity.getBrand() + "')]").exists());
    }
    @Test
    public void testGetProductBySearchKeyType() throws Exception {
        var actualEntity = createProduct();
        mockMvc.perform(get("/api/products/search/{searchKey}",actualEntity.getType())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == '" + actualEntity.getName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.description == '" + actualEntity.getDescription() + "')]").exists())
                .andExpect(jsonPath("$[?(@.price == '" + actualEntity.getPrice() + "')]").exists())
                .andExpect(jsonPath("$[?(@.type == '" + actualEntity.getType() + "')]").exists())
                .andExpect(jsonPath("$[?(@.brand == '" + actualEntity.getBrand() + "')]").exists());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        var actualEntity = createProduct();

        mockMvc.perform(delete("/api/products/remove/{name}", actualEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
        Assertions.assertEquals(0,productRepository.count());
    }

    @Test
    public void testUpdatePriceProduct() throws Exception {
        var actualEntity = createProduct();

        mockMvc.perform(put("/api/products/edit/price/{name}", actualEntity.getName())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                 {
                    "price" : 55.00
                 }
                """)
        ).andExpect(status().isOk());

        Optional<Product> byName = productRepository.findByName("isolate");
        assertEquals(55.00,byName.get().getPrice());
    }

    @Test
    public void testAddProductToCart() throws Exception {
        var actualEntity = createProduct();
        mockMvc.perform(get("/api/products/cart/{name}",actualEntity.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.name == '" + actualEntity.getName() + "')]").exists())
                .andExpect(jsonPath("$[?(@.price == '" + actualEntity.getPrice() + "')]").exists());
    }

    @Test
    public void testAddProductToCart_NotExist() throws Exception {
        mockMvc.perform(get("/api/products/cart/{name}","test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    private Product createProduct(){
       Product product = new Product();
        product.setName("isolate");
        product.setPrice(50.00);
        product.setType("SPORT");
        product.setBrand("AMIX");
        product.setImageUrl("test");
        product.setDescription("product test");
        productRepository.save(product);
        return product;
    }
}
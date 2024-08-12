package bg.softuni.exam.healthNutrition.Rest.repository;

import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    private Product product;
    @BeforeEach
    public void setUp(){
        product = new Product();
        product.setName("isolate");
        product.setPrice(50.00);
        product.setType("SPORT");
        product.setBrand("AMIX");
        product.setImageUrl("test");
        product.setDescription("product test");
        productRepository.save(product);
    }

    @AfterEach
    public void tearDown(){
        productRepository.deleteAll();
    }

    @Test
    public void testAddSameProduct(){
        productRepository.save(product);
        assertEquals(1,productRepository.count());

    }
    @Test
    public void testFindByName(){
        Optional<Product> byName = productRepository.findByName("isolate");
        assertEquals(byName.get().getName(),"isolate");
    }
    @Test
    public void testNotExistProduct(){
        Optional<Product> byName = productRepository.findByName("fat burner");
        assertTrue(byName.isEmpty());
    }
    @Test
    public void testDeleteByID(){
        productRepository.deleteById(product.getId());
        assertEquals(0,productRepository.count());
    }

}
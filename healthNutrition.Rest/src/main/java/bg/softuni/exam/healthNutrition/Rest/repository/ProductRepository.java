package bg.softuni.exam.healthNutrition.Rest.repository;

import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // find product by name - name is unique
    Optional<Product> findByName(String value);

    // for search bar
    List<Product> findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrTypeContainingIgnoreCase(
            String key1, String key2,String key3);









}

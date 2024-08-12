package bg.softuni.exam.healthNutrition.Rest.web;

import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products/")
public class RestControllerGetProducts {
    // very simple rest controller  to do more specific
           // TODO
    private final ProductService productService;

    public RestControllerGetProducts(ProductService productService) {
        this.productService = productService;
    }

    // get all products permit all
    @GetMapping
    public ResponseEntity<List<ProductDetailsDTO>> getAllOffers() {
        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }
    // get product by uuid permit all
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<ProductDetailsDTO> getById(@PathVariable("id") Long id) {
       Optional<ProductDetailsDTO> productDetails = productService.getProductDetails(id);

        return productDetails.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // remove product by uuid permit only admin

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ProductDetailsDTO> deleteById(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity
                .noContent()
                .build();
    }
    // create product  permit only admin
    @PostMapping("/create")
    public ResponseEntity<ProductCreateDTO> createOffer(
            @RequestBody ProductCreateDTO productCreateDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Long id = this.productService.addProduct(productCreateDTO);
        return ResponseEntity.created(
                uriComponentsBuilder.path("/api/product/{id}").build(id)
        ).build();
    }
     // get products by key
    @GetMapping("/search/{searchKey}")
    public ResponseEntity<List<ProductDetailsDTO>> searchBy(@PathVariable("searchKey") String searchKey) {
        return ResponseEntity.ok(productService.getProductsByKey(searchKey));
    }


}

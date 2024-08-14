package bg.softuni.exam.healthNutrition.Rest.web;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductEditPrice;
import bg.softuni.exam.healthNutrition.Rest.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class RestControllerGetProducts {
    // very simple rest controller  to do more specific
           // TODO
    private final ProductService productService;

    public RestControllerGetProducts(ProductService productService) {
        this.productService = productService;
    }

    // get all products permit all
    @GetMapping
    public ResponseEntity<List<ProductDetailsDTO>> getAllProducts() {
        return ResponseEntity.ok(
                productService.getAllProducts()
        );
    }
    // get product by uuid permit all
    @GetMapping(value = "/get/{name}")
    public ResponseEntity<ProductDetailsDTO> getById(@PathVariable("name") String name) {
       Optional<ProductDetailsDTO> productDetails = productService.getProductDetails(name);
        return productDetails.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // remove product by uuid permit only admin

    @DeleteMapping("/remove/{name}")
    public ResponseEntity<ProductDetailsDTO> deleteById(@PathVariable("name") String name) {
        productService.deleteProduct(name);
        return ResponseEntity
                .noContent()
                .build();
    }
    // create product  permit only admin
    @PostMapping("/create")
    public ResponseEntity<ProductDetailsDTO> createProduct(
            @RequestBody ProductCreateDTO productCreateDTO
    ) {
        ProductDetailsDTO productDetailsDTO = this.productService.addProduct(productCreateDTO);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                                .path("/{id}")
                        .buildAndExpand(productDetailsDTO.getId())
                        .toUri()
        ).body(productDetailsDTO);
    }

    @PutMapping("/edit/price/{name}")
    public ResponseEntity<ProductDetailsDTO> editPrice(@PathVariable("name") String name,
                                                       @RequestBody ProductEditPrice productEditPrice) {
        Optional<ProductDetailsDTO> productDetailsDTO = this.productService.editPrice(name,productEditPrice);
      return productDetailsDTO.map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
    }




}

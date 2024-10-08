package bg.softuni.exam.healthNutrition.Rest.service.impl;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductEditPrice;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductInCartDTO;
import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import bg.softuni.exam.healthNutrition.Rest.repository.ProductRepository;
import bg.softuni.exam.healthNutrition.Rest.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;



    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @Override
    // add product
    public ProductDetailsDTO addProduct(ProductCreateDTO productCreateDTO){

        if (productRepository.findByName(productCreateDTO.getName()).isPresent()) {
            throw new IllegalArgumentException("Name is already exist!");
        }
        Product save = this.productRepository.save(map(productCreateDTO));

        return mapAsDetails(save);
    }



    @Override
    // get all product
    public List<ProductDetailsDTO> getAllProducts() {
            return productRepository.findAll()
                    .stream().map(ProductServiceImpl::mapAsDetails).collect(Collectors.toList());
    }


    @Override
    // get product details with id
    public Optional<ProductDetailsDTO> getProductDetails(String name) {
        Optional<Product> byName = productRepository.findByName(name);
        if (byName.isEmpty()) {
            throw new IllegalArgumentException("Product is not exist");
        }
        return  productRepository.findByName(name).map(ProductServiceImpl::mapAsDetails);
    }

    @Override
    @Transactional
    // delete product  with id
    public void deleteProduct(String name) {
        this.productRepository.deleteProductByName(name);
    }

    @Override
    public List<ProductDetailsDTO> getProductsBySearchKey(String searchKey) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrTypeContainingIgnoreCase(searchKey,searchKey,searchKey);
        return products.stream().map(ProductServiceImpl::mapAsDetails).collect(Collectors.toList());
    }

    @Override
    // edit product price
    public Optional<ProductDetailsDTO> editPrice(String name, ProductEditPrice productEditPrice) {
        Optional<Product> byName = productRepository.findByName(name);
        if (byName.isEmpty()) {
            throw new IllegalArgumentException("Product is not exist");
        }
        byName.get().setPrice(productEditPrice.getPrice());
        productRepository.save(byName.get());
        return byName.map(ProductServiceImpl::mapAsDetails);
    }
     // add product in cart
    @Override
    public ProductInCartDTO productInCart(String name) {
        Optional<Product> byName = productRepository.findByName(name);
        if (byName.isEmpty()) {
            throw new IllegalArgumentException("Product is not exist");
        }
        ProductInCartDTO product = new ProductInCartDTO();
        product.setName(byName.get().getName());
        product.setPrice(byName.get().getPrice());
        product.increaseQuantity();
        return product;
    }


    // map product entity to productDetailsDTO
    private static ProductDetailsDTO mapAsDetails(Product product) {
        ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO();
        productDetailsDTO.setId(product.getId());
        productDetailsDTO.setName(product.getName());
        productDetailsDTO.setDescription(product.getDescription());
        productDetailsDTO.setPrice(product.getPrice());
        productDetailsDTO.setType(product.getType());
        productDetailsDTO.setBrand(product.getBrand());
        productDetailsDTO.setImageUrl(product.getImageUrl());
        return  productDetailsDTO;
    }


    // create product from ProductCreateDTO
    private Product map(ProductCreateDTO productCreateDTO) {
        Product product = new Product();
        product.setName(productCreateDTO.getName());
        product.setDescription(productCreateDTO.getDescription());
        product.setImageUrl(productCreateDTO.getImageUrl());
        product.setPrice(productCreateDTO.getPrice());
        product.setType(productCreateDTO.getType());
        product.setBrand(productCreateDTO.getBrand());
        return product;
    }





}

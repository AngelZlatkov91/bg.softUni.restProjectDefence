package bg.softuni.exam.healthNutrition.Rest.service.impl;

import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductCreateDTO;
import bg.softuni.exam.healthNutrition.Rest.model.DTO.ProductDetailsDTO;
import bg.softuni.exam.healthNutrition.Rest.model.entity.Product;
import bg.softuni.exam.healthNutrition.Rest.repository.ProductRepository;
import bg.softuni.exam.healthNutrition.Rest.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;



    public ProductServiceImpl(ProductRepository productRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    // add product
    public Long addProduct(ProductCreateDTO productCreateDTO){
        Product save = this.productRepository.save(map(productCreateDTO));
        return save.getId();
    }



    @Override
    // get all product
    public List<ProductDetailsDTO> getAllProducts() {
            return productRepository.findAll()
                    .stream().map(ProductServiceImpl::mapAsDetails).collect(Collectors.toList());
    }

    @Override
    // get product by key
    public List<ProductDetailsDTO> getProductsByKey(String searchKey) {
        List<ProductDetailsDTO> collect = productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrTypeContainingIgnoreCase(
                searchKey, searchKey, searchKey).stream().map(ProductServiceImpl::mapAsDetails).collect(Collectors.toList());
        searchKey = "";
        return collect;
    }

    @Override
    // get product details with id
    public Optional<ProductDetailsDTO> getProductDetails(Long id) {
        return  productRepository.findById(id).map(ProductServiceImpl::mapAsDetails);
    }

    @Override
    // delete product  with id
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
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
        return  productDetailsDTO;
    }


    // create product from ProductCreateDTO
    private Product map(ProductCreateDTO productCreateDTO) {
            return this.mapper.map(productCreateDTO,Product.class);
    }





}

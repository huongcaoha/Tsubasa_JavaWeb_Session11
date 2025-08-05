package com.ra.session11.service;

import com.ra.session11.model.dto.ProductDTO;
import com.ra.session11.model.entity.Product;
import com.ra.session11.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.products();
    }

    @Transactional(readOnly = true)
    public Product findById(long id) {
        return productRepository.getProductById(id);
    }

    @Transactional
    public Product save(ProductDTO productDTO) {
        Product product = convertProductDTOToProduct(productDTO);
        if (productDTO.getImage() != null) {
            String urlImage = cloudinaryService.upload(productDTO.getImage());
            product.setImage(urlImage);
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product update(ProductDTO productDTO,long id) {
        Product oldProduct = findById(id);
        Product product = convertProductDTOToProduct(productDTO);
        product.setId(id);
        if (productDTO.getImage() != null && !productDTO.getImage().isEmpty()) {
            String urlImage = cloudinaryService.upload(productDTO.getImage());
            product.setImage(urlImage);
        }else {
            product.setImage(oldProduct.getImage());
        }
        return productRepository.save(product);
    }

    public Product convertProductDTOToProduct(ProductDTO productDTO) {
        return Product
                .builder()
                .productName(productDTO.getProductName())
                .status(productDTO.isStatus())
                .stock(productDTO.getStock())
                .price(productDTO.getPrice())
                .build();
    }

    public ProductDTO convertProductToProductDTO(Product product) {
        return ProductDTO
                .builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .stock(product.getStock())
                .status(product.isStatus())
                .build();
    }

    @Transactional
    public boolean deleteById(long id) {
        Product product = productRepository.getProductById(id);
        if (product != null) {
            return productRepository.deleteProductById(product);
        }else {
            return false;
        }
    }
}

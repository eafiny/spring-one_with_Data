package ru.geekbrains.spring.one.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.spring.one.model.Product;
import ru.geekbrains.spring.one.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Product> findOneById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void incrementPriceById(Long id, int amount) {
        Product p = productRepository.findById(id).get();
        p.incrementPrice(amount);
    }

    private OptionalInt getMaxPrice(){
        return productRepository.findAll()
                .stream()
                .mapToInt(Product::getPrice)
                .max();
    }

    public Page<Product> getFilteredProductsByPrice (Integer minPrice, Integer maxPrice, int pageSize){
        if (minPrice == null) minPrice = 0;
        if (maxPrice == null) maxPrice = getMaxPrice().getAsInt();
        return productRepository.findAllByPriceBetween(minPrice, maxPrice, PageRequest.of(0,pageSize, Sort.by("price")));
    }

    public Page<Product> getFilteredProductsByTitlePart (String titlePart, int pageSize){
        return productRepository.findByTitleContains(titlePart, PageRequest.of(0, 5));
    }
}

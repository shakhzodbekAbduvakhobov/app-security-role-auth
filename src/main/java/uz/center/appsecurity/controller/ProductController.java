package uz.center.appsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.center.appsecurity.entity.Product;
import uz.center.appsecurity.repository.ProductRepository;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //    @PreAuthorize(value = "hasAnyRole('MANAGER', 'DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public HttpEntity<?> getProduct() {

//        Kimda qaysi role borligini bilish
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Product> productRepositoryAll = productRepository.findAll();
        return ResponseEntity.ok(productRepositoryAll);
    }

    //    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public HttpEntity<?> addProduct(@RequestBody Product product) {
        Product product1 = productRepository.save(product);
        return ResponseEntity.ok(product1);
    }

    //    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public HttpEntity<?> editProduct(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product1 = optionalProduct.get();
            product1.setName(product.getName());
            productRepository.save(product1);
            return ResponseEntity.ok(product1);
        }
        return ResponseEntity.status(404).build();
    }

    //    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('READ_ONE_PRODUCT')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @PreAuthorize(value = "hasAuthority('DELETE_PRODUCT')")
    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isPresent() ? 200 : 404).body(optionalProduct.orElse(null));
    }
}

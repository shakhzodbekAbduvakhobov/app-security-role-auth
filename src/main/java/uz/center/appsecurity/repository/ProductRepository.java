package uz.center.appsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.center.appsecurity.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

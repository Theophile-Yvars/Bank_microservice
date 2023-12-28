package fr.unice.polytech.primabank.bankerservice.repositories;

import fr.unice.polytech.primabank.bankerservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {}

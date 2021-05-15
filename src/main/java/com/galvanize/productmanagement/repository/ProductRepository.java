package com.galvanize.productmanagement.repository;

import com.galvanize.productmanagement.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long>, PagingAndSortingRepository<Product, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> getByIdAndDeletedIsFalse(Long id);

    @Query("select p from Product p where p.viewCount > 0 and p.deleted = false order by p.viewCount DESC")
    List<Product> findMostVisitedProducts(Pageable pageable);
}

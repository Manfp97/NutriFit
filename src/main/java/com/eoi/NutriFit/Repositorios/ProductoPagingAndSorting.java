package com.eoi.NutriFit.Repositorios;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;



@NonNullApi
@NoRepositoryBean
public interface ProductoPagingAndSorting<T, ID> extends JpaRepository<T, ID> {
    List<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);

}
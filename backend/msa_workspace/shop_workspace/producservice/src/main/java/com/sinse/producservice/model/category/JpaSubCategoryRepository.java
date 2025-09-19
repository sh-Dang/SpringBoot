package com.sinse.producservice.model.category;

import com.sinse.producservice.domain.SubCategory;
import com.sinse.producservice.domain.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSubCategoryRepository extends JpaRepository<SubCategory, Integer> {
    public List<SubCategory> findByTopCategory_topCategoryId(
        int topCategoryId
    );
}

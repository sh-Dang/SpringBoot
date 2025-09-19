package com.sinse.producservice.model.category;

import com.sinse.producservice.domain.SubCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService{

    private final JpaSubCategoryRepository jpaSubCategoryRepository;
    public SubCategoryServiceImpl(JpaSubCategoryRepository jpaSubCategoryRepository) {
        this.jpaSubCategoryRepository = jpaSubCategoryRepository;
    }

    @Override
    public List<SubCategory> selectAll() {
        return List.of();
    }

    @Override
    public List<SubCategory> selectAll(int topCategoryId) {
        return jpaSubCategoryRepository.findByTopCategory_topCategoryId(topCategoryId);
    }
}

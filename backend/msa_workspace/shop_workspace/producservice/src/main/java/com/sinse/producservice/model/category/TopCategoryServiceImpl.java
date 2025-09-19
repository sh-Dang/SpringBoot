package com.sinse.producservice.model.category;

import com.sinse.producservice.domain.TopCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopCategoryServiceImpl implements TopCategoryService {

    private final JpaTopCategoryRepository jpaTopCategoryRepository;
    public TopCategoryServiceImpl(JpaTopCategoryRepository jpaTopCategoryRepository) {
        this.jpaTopCategoryRepository = jpaTopCategoryRepository;
    }

    @Override
    public List<TopCategory> selectAll() {
        return jpaTopCategoryRepository.findAll();
    }
}

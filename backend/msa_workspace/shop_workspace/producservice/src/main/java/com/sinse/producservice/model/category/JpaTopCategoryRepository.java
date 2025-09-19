package com.sinse.producservice.model.category;

import com.sinse.producservice.domain.TopCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTopCategoryRepository extends JpaRepository<TopCategory, Integer> {

    //외래키를 이용하여 목록 가져오기

}

package com.sinse.producservice.model.category;

import com.sinse.producservice.domain.SubCategory;

import java.util.List;

public interface SubCategoryService {
    //모두 가져오기
    public List<SubCategory> selectAll();

    //특정 상위에 소속된 목록만 가져오기
    public List<SubCategory> selectAll(int topCategoryId);
}

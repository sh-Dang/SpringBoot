package com.sinse.electroshop.model.store;

import com.sinse.electroshop.domain.Store;

import java.util.List;

public interface StoreDAO {

    //상점 등록
    public Store save(Store store);

    //상점 한건 가져오기
    public Store findById(int storeId);

    //모든 상점 가져오기
    public List<Store> findAll();

    //id와 password로 유저 찾기
    public Store login(Store store);

    //상점 정보 수정
    public Store update(Store store);

    //상점 정보 삭제
    public void deleteById(int storeId);
}


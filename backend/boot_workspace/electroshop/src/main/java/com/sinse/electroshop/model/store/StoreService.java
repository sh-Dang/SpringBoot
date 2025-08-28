package com.sinse.electroshop.model.store;


import com.sinse.electroshop.domain.Store;
import com.sinse.electroshop.exception.StoreNotFoundException;

public interface StoreService {
    public Store register(Store store);
    public Store login(Store store) throws StoreNotFoundException;
}
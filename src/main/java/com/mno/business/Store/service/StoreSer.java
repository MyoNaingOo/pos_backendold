package com.mno.business.Store.service;


import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.Store.entity.Store;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.entity.Product;
import com.mno.business.user.entity.User;
import com.mno.business.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreSer {

    private final StoreRepo storeRepo;
    private final ProductRepo productRepo;
    private final UserService userService;

    public void addStore(Store store) {
        storeRepo.save(store);
    }

    public Optional<Store> getStore(Long id) {
        return storeRepo.findById(id);
    }


    public List<Store> storeList(int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findAll(pageable).getContent();
    }

    public List<Store> storeListAll() {
        return storeRepo.findAll(Sort.by("id").descending());
    }

    public void deleteStore(Long id) {
        Store store = getStore(id).orElse(null);
        assert store != null;
        if (0 >= store.getUpdate_bulk()) {
            storeRepo.deleteById(id);
        }
    }

    public List<Store> findByMonthAndYear(int month, int year,int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.findByMonthAndYear(month, year,pageable);
    }


    public List<Store> findAllByProduct(Long product_id,int num){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        Product product = productRepo.findById(product_id).orElse(null);
        return storeRepo.findAllByProduct(product,pageable);

    }

    public void updateBulkForSale(Long product_id, int updateBulk) {

        Store available = storeRepo.findAvailable(product_id).orElse(null);
        if (available != null) {
            int fistStore = available.getBulk() - available.getUpdate_bulk();
            if (fistStore < updateBulk) {
                storeRepo.changeUpdateBulk(available.getId(), available.getUpdate_bulk() + fistStore);
                updateBulkForSale(product_id, updateBulk - fistStore);
            } else {
                storeRepo.changeUpdateBulk(available.getId(), available.getUpdate_bulk() + updateBulk);
            }
        }
    }


    public void deleteFormSale(Long product_id, int delete_bulk) {
        Store available = storeRepo.findNowUseStatusByProduct(product_id).orElse(null);
        if (available != null) {

            if (delete_bulk > available.getUpdate_bulk()) {
                int secondStore = delete_bulk - available.getUpdate_bulk();
                storeRepo.changeUpdateBulk(available.getId(), 0);
                deleteFormSale(product_id, secondStore);
            } else {
                int max_delete = available.getUpdate_bulk() - delete_bulk;
                storeRepo.changeUpdateBulk(available.getId(), max_delete);
            }
        }
    }

    public Integer getProductBalance(Long product_id) {
        return storeRepo.getProBalance(product_id).orElse(0);
    }



    public List<Store> findAllByUser(Long user_id,int num){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        User user = userService.getUser(user_id).orElse(null);

        return storeRepo.findAllByUser(user,pageable);
    }

    public List<Store> getProductsBalance(int num){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsBalance(pageable);
    }

    public List<Store> getProductsSold(int num){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return storeRepo.getProductsSold(pageable);
    }
}

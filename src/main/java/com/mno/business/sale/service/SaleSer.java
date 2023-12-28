package com.mno.business.sale.service;


import com.mno.business.Store.service.StoreSer;
import com.mno.business.sale.Repo.SaleProRepo;
import com.mno.business.sale.Repo.SaleRepo;
import com.mno.business.sale.entity.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleSer {

    private final SaleRepo saleRepo;
    private final SaleProRepo saleProRepo;
    private final StoreSer storeSer;


    public void add(Sale sale) {
        saleRepo.save(sale);
        sale.getSalePros().forEach(salePro -> {
            storeSer.updateBulkForSale(salePro.getProduct_id(), salePro.getBulk());
        });
    }


    public Sale getSale(Long id) {
        return saleRepo.findById(id).orElse(null);
    }

    public List<Sale> sales(int page_num) {
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findAll(pageable).getContent();
    }

    @Async
    public void delete(Long id) {
        Sale sale = getSale(id);
        sale.getSalePros().forEach(
                salePro -> {
                    storeSer.deleteFormSale(salePro.getProduct_id(), salePro.getBulk());
                });
        saleRepo.deleteById(id);
    }

    public List<Sale> sales() {
        return saleRepo.findAll(Sort.by("id").descending());
    }


    public List<Sale> findByMonth(int month,int year,int page_num){
        Pageable pageable = PageRequest.of(page_num, 20, Sort.by("id").descending());
        return saleRepo.findByMonth(month,year,pageable);
    }


}

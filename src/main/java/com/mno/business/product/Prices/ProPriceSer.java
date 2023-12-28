package com.mno.business.product.Prices;

import com.mno.business.product.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProPriceSer {

    @Autowired
    private ProPriceRepo proPriceRepo;

    public void add(ProPrice proPrice){
        proPriceRepo.save(proPrice);
    }

    public ProPrice findById(Long id){
        return proPriceRepo.findById(id).orElse(null);
    }

    public List<ProPrice> findAllByProduct(Product product){
        return proPriceRepo.findAllByProduct(product);
    }

    public ProPrice LtsProPrice(Long product_id){
        return proPriceRepo.findLtsProPrice(product_id).orElse(null);
    }

    public void deleteById(Long id){
        proPriceRepo.deleteById(id);
    }


}

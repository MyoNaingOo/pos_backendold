package com.mno.business.Store.controller;


import com.mno.business.Store.dto.StoreDto;
import com.mno.business.Store.entity.Store;
import com.mno.business.Store.service.StoreSer;
import com.mno.business.config.JwtService;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.product.entity.Product;
import com.mno.business.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/store")
@RequiredArgsConstructor
public class StoreControl {

    private final JwtService jwtService;
    private final StoreSer storeSer;
    private final ProductRepo productRepo;


    @PostMapping("add")
    private void addStore(@RequestBody StoreDto storeDto, HttpServletRequest request) {

        User user = jwtService.getuser(request);
        Product product = productRepo.findById(storeDto.getProduct()).orElse(null);
        Store store = Store.builder()
                .product(product)
                .user(user)
                .bulk(storeDto.getBulk())
                .update_bulk(0)
                .time(LocalDateTime.now())
                .build();
        storeSer.addStore(store);
    }

    @GetMapping("stores")
    private List<Store> stores() {
        return storeSer.storeListAll();
    }

    @GetMapping("page/{num}")
    private List<Store> page(@PathVariable("num") int id) {
        return storeSer.storeList(id);
    }

    @GetMapping("prosBalance/{num}")
    public List<Store> getProductsBalance(@PathVariable("num") int num) {
        return storeSer.getProductsBalance(num);
    }

    @GetMapping("sold/{num}")
    public List<Store> getProductsSold(@PathVariable("num") int num) {
        return storeSer.getProductsSold(num);
    }


    @GetMapping("findAllByMonth/{num}")
    private List<Store> getByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year,
            @PathVariable("num") int num) {
        return storeSer.findByMonthAndYear(month, year, num);
    }


    @GetMapping("findAllByProduct/{num}")
    private List<Store> getAllByProduct(@PathVariable("num") int num, @RequestParam("product_id") Long product_id) {
        return storeSer.findAllByProduct(product_id, num);
    }

    @GetMapping("findAllByUser/{num}")
    public List<Store> findAllByUser(@RequestParam("user_id") Long user_id, @PathVariable("num") int num) {
        return storeSer.findAllByUser(user_id, num);
    }

    @DeleteMapping("delete/{id}")
    private void delete(@PathVariable("id") Long id) {
        storeSer.deleteStore(id);
    }


}

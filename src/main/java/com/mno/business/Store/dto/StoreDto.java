package com.mno.business.Store.dto;



import com.mno.business.Store.entity.Store;
import com.mno.business.product.dto.ProductDto;
import com.mno.business.product.entity.Product;
import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {


    private Long id;
    private Long product_id;
    private int bulk;
    private LocalDateTime time;
    private User user;
    private ProductDto product;
    private int update_bulk;




}

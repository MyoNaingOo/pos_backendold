package com.mno.business.sale.dto;


import com.mno.business.product.entity.Product;
import com.mno.business.sale.entity.SalePro;
import com.mno.business.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {

    private User user;
    private LocalDateTime time;
    private List<SaleProDto> saleProList;



}

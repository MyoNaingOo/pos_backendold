package com.mno.business.Store.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDto {


    private Long id;
    private Long product;
    private Long user;
    private int bulk;
    private LocalDateTime time;
    private boolean available;



}

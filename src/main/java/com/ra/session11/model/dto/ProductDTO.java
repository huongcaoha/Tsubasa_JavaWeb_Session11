package com.ra.session11.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDTO {
    @NotBlank(message = "product can not blank")
    private String productName;

    @NotNull(message = "price can not blank")
    @Min(1)
    private double price;

    @NotNull(message = "stock can not blank")
    @Min(1)
    private int stock;
    private MultipartFile image;
    private boolean status;
}

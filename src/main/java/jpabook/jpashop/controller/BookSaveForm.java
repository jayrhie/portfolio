package jpabook.jpashop.controller;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookSaveForm {

    private Long id;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @NotNull(message = "가격을 입력해주세요.")
    @Range(min = 1_000, max = 1_000_000, message = "가격은 1,000원부터 1,000,000원까지 가능합니다.")
    private Integer price;

    @NotNull(message = "수량을 입력해주세요.")
    @Max(value = 9_999, message = "수량은 최대 9,999개까지 가능합니다.")
    private Integer stockQuantity;

    @NotBlank(message = "작가명은 필수입니다.")
    private String author;

    @NotBlank(message = "isbn은 필수 입력 사항입니다.")
    private String isbn;

}

package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "회원 이름은 필수 입니다.")
    private String name;

    @NotEmpty(message = "주소는 필수 입니다.")
    private String city;

    @NotEmpty(message = "상세 주소를 입력해 주세요.")
    private String street;

    @NotEmpty(message = "우편코드는 필수 입니다.")
    private String zipcode;
}

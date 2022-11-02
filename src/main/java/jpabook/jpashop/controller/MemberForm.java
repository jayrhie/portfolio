package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberForm {

    @NotBlank(message = "회원 이름은 필수 입니다.")
    private String name;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 4, max = 16, message = "패스워드는 4~16자까지 가능합니다.")
    private String password;

    @NotBlank(message = "주소는 필수 입니다.")
    private String city;

    @NotBlank(message = "상세 주소를 입력해 주세요.")
    private String street;

    @NotBlank(message = "우편코드는 필수 입니다.")
    private String zipcode;
}

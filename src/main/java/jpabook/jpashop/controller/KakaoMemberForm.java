package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
public class KakaoMemberForm {

    @NotNull
    private Long kakaoId;

    @NotBlank(message = "회원 이름은 필수 입니다.")
    private String name;

    @NotBlank(message = "패스워드를 입력해주세요.")
    @Size(min = 4, max = 16, message = "패스워드는 4~16자까지 가능합니다.")
    private String password;

    private String city;

    private String street;

    private String zipcode;
}

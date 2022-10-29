package jpabook.jpashop.domain.login;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String name, String password) {
        return memberRepository.findByName(name)
                .stream().filter(member -> member.getPassword().equals(password)).findAny().orElse(null);
    }
}

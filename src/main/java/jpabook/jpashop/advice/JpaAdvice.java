package jpabook.jpashop.advice;

import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class JpaAdvice {

    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateException(Exception ex, Model model) {
        log.error("Exception 발생 : {}", ex.getMessage());
        model.addAttribute("msg", "중복된 회원 이름입니다.");
        return "exception/exception";
    }

    @ExceptionHandler(NotEnoughStockException.class)
    public String notEnoughStockException(Exception ex, Model model) {
        log.error("Exception 발생 : {}", ex.getMessage());
        model.addAttribute("msg", "주문 수량이 재고 수량보다 많을 수 없습니다.");
        return "exception/exception";
    }
}

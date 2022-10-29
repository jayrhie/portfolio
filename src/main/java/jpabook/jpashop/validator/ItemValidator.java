package jpabook.jpashop.validator;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz); //파라메터로 넘어오는 clazz가 Item에 지원이 되냐?
        //item == clazz    //파라메터로 넘어온 clazz가 Item 객체일 때
        //item == subItem  //Item을 상속받은 객체여도 ok.
    }

    @Override
    public void validate(Object target, Errors errors) { //Errors는 BindingResults의 부모 클래스
        Item item = (Item) target;

        //검증 로직
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required"); //아래의 if (!StringUtils.hasText(item.getItemName())) {...}와 같은 기능을 하는 유틸. 단 Empty와 공백같은 기능만 제공.
        if (!StringUtils.hasText(item.getName())) { //StringUtils는 Spring것으로 사용하도록 하자.
            errors.rejectValue("itemName", "required");
        }
        if (item.getPrice() == null || item.getPrice() < 1_000 || item.getPrice() > 1_000_000) {
            errors.rejectValue("price", "range", new Object[]{1_000, 1_000_000}, null);
        }
        if (item.getStockQuantity() == null || item.getStockQuantity() >= 9_999) {
            errors.rejectValue("quantity", "max", new Object[]{9_999}, null);
        }

        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getStockQuantity() != null) {
            int resultPrice = item.getPrice() * item.getStockQuantity();
            if (resultPrice < 10_000) {
                errors.reject("totalPriceMin", new Object[]{10_000, resultPrice}, null);
            }
        }
    }
}

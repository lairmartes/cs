package com.cs.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cs.enums.OrderType;

public class EnumOrderTypePatternValidator implements ConstraintValidator<EnumOrderTypePattern, OrderType>{
	
	private OrderType[] subset;
	
	public EnumOrderTypePatternValidator() {
		super();
	}
	
    @Override
    public void initialize(EnumOrderTypePattern constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(OrderType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}

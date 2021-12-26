package com.cs.validation;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cs.enums.ExecutionOrderResultType;

public class EnumOrderExecutionTypePatternValidator implements ConstraintValidator<EnumOrderExecutionTypePattern, ExecutionOrderResultType>{
	
	private ExecutionOrderResultType[] subset;
	
	public EnumOrderExecutionTypePatternValidator() {
		super();
	}
	
    @Override
    public void initialize(EnumOrderExecutionTypePattern constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(ExecutionOrderResultType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}

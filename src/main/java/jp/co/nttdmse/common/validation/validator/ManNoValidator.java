package jp.co.nttdmse.common.validation.validator;

import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import jp.co.nttdmse.common.validation.annotation.ManNo;

public class ManNoValidator implements ConstraintValidator<ManNo, Object>{

	private String message;

	@Override
	public void initialize(ManNo constraintAnnotation) {
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(Objects.isNull(value)) {
			return true;
		}

		if(!Objects.equals(Integer.toString((Integer)value).length(), 6)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}
		return true;
	}

}

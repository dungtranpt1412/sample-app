package jp.co.nttdmse.app.form;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringBootTest
class EmployeeInfoFormTest {

	@Autowired
	Validator validator;

	private EmployeeInfoForm employeeInfoForm;
	private BindingResult bindingResult;

	/* nullチェック */
	@Test
	void testNotNull() {
		employeeInfoForm = new EmployeeInfoForm();
		bindingResult = new BindException(employeeInfoForm, "employeeInfoForm");
		employeeInfoForm.setManno(null);
		employeeInfoForm.setName(null);
		employeeInfoForm.setJoinDate(null);
		employeeInfoForm.setSex(null);
		employeeInfoForm.setEnrollmentType(null);
		employeeInfoForm.setTimeSaving(null);
		validator.validate(employeeInfoForm, bindingResult);
		assertThat(bindingResult.getFieldError("manno").getDefaultMessage(), is("{0}は必須入力項目です"));
		assertThat(bindingResult.getFieldError("name").getDefaultMessage(), is("{0}は必須入力項目です"));
		assertThat(bindingResult.getFieldError("joinDate").getDefaultMessage(), is("{0}は必須入力項目です"));
		assertThat(bindingResult.getFieldError("sex").getDefaultMessage(), is("{0}は必須入力項目です"));
		assertThat(bindingResult.getFieldError("enrollmentType").getDefaultMessage(), is("{0}は必須入力項目です"));
		assertThat(bindingResult.getFieldError("timeSaving").getDefaultMessage(), is("{0}は必須入力項目です"));
	}

	/* 名前入力欄の空白チェック */
	@Test
	void testNameNotBlank() {
		employeeInfoForm = new EmployeeInfoForm();
		bindingResult = new BindException(employeeInfoForm, "employeeInfoForm");
		employeeInfoForm.setName("");
		validator.validate(employeeInfoForm, bindingResult);
		assertThat(bindingResult.getFieldError("name").getDefaultMessage(), is("{0}は空欄にできません"));
	}

	/* 名前の文字数チェック */
	@Test
	void testNameMaxSize() {
		employeeInfoForm = new EmployeeInfoForm();
		bindingResult = new BindException(employeeInfoForm, "employeeInfoForm");
		employeeInfoForm.setName("1234567890123456789012");
		validator.validate(employeeInfoForm, bindingResult);
		assertThat(bindingResult.getFieldError("name").getDefaultMessage(), is("{0}は20文字以下です"));
	}

	/* 社員番号の桁不足チェック */
	@Test
	void testMannoShortDigit() {
		employeeInfoForm = new EmployeeInfoForm();
		bindingResult = new BindException(employeeInfoForm, "employeeInfoForm");
		employeeInfoForm.setManno(111);
		validator.validate(employeeInfoForm, bindingResult);
		assertThat(bindingResult.getFieldError("manno").getDefaultMessage(), is("{0}は6桁です"));
	}

	/* 社員番号の桁あふれチェック */
	@Test
	void testMannoTooLongDigit() {
		employeeInfoForm = new EmployeeInfoForm();
		bindingResult = new BindException(employeeInfoForm, "employeeInfoForm");
		employeeInfoForm.setManno(11111111);
		validator.validate(employeeInfoForm, bindingResult);
		assertThat(bindingResult.getFieldError("manno").getDefaultMessage(), is("{0}は6桁です"));
	}

	/* Setterでセットした値がGetterで取得できることを確認 */
	@Test
	void testSetterAndGetter() {
		employeeInfoForm = new EmployeeInfoForm();
		employeeInfoForm.setManno(111111);
		employeeInfoForm.setName("Form Test");
		employeeInfoForm.setJoinDate(Date.valueOf("2020-01-01"));
		employeeInfoForm.setSex("M");
		employeeInfoForm.setEnrollmentType("0");
		employeeInfoForm.setTimeSaving(false);
		assertThat(employeeInfoForm.getManno(), is(111111));
		assertThat(employeeInfoForm.getName(), is("Form Test"));
		assertThat(employeeInfoForm.getJoinDate(), is(Date.valueOf("2020-01-01")));
		assertThat(employeeInfoForm.getSex(), is("M"));
		assertThat(employeeInfoForm.getEnrollmentType(), is("0"));
		assertThat(employeeInfoForm.getTimeSaving(), is(false));
	}

}

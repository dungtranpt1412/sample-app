package jp.co.nttdmse.app.helper;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;
import jp.co.nttdmse.app.form.EmployeeInfoForm;
import jp.co.nttdmse.common.Const;

@SpringBootTest
class EmployeeInfoHelperTest {

	// employeeData1
	final Integer MANNO1 = 111111;
	final String NAME1 = "Test1";
	final Date JOIN_DATE1 = Date.valueOf("2019-12-01");
	final String SEX1 = "M";
	final String ENROLLMENT_TYPE1 ="0";
	final boolean TIME_SAVING1 = false;

	// employeeData2
	final Integer MANNO2 = 222222;
	final String NAME2 = "Test2";
	final Date JOIN_DATE2 = Date.valueOf("2019-11-01");
	final String SEX2 = "F";
	final String ENROLLMENT_TYPE2="1";
	final boolean TIME_SAVING2 = true;

	// employeeData3
	final Integer MANNO3 = 333333;
	final String NAME3 = "Test3";
	final Date JOIN_DATE3 = Date.valueOf("2019-10-01");
	final String SEX3 = "M";
	final String ENROLLMENT_TYPE3 ="9";
	final boolean TIME_SAVING3 = false;

	@Autowired
	EmployeeInfoHelper employeeInfoHelper;

	@Test
	void testHelpEmployeeInfoToFormWhenHasData() {
		List<EmployeeInfoBean> beanList = new ArrayList<EmployeeInfoBean>();
		beanList.add(setEmployeeInfoBean(MANNO1, NAME1, JOIN_DATE1, SEX1, ENROLLMENT_TYPE1, TIME_SAVING1));
		beanList.add(setEmployeeInfoBean(MANNO2, NAME2, JOIN_DATE2, SEX2, ENROLLMENT_TYPE2, TIME_SAVING2));
		beanList.add(setEmployeeInfoBean(MANNO3, NAME3, JOIN_DATE3, SEX3, ENROLLMENT_TYPE3, TIME_SAVING3));
		List<EmployeeInfoForm> resultList = employeeInfoHelper.helpEmployeeInfoToForm(beanList);

		// assertion
		assertThat(resultList.size(), is(3));
		assertEmployeeInfoHelper(resultList.get(0), MANNO1, NAME1, JOIN_DATE1, SEX1, ENROLLMENT_TYPE1, TIME_SAVING1);
		assertEmployeeInfoHelper(resultList.get(1), MANNO2, NAME2, JOIN_DATE2, SEX2, ENROLLMENT_TYPE2, TIME_SAVING2);
		assertEmployeeInfoHelper(resultList.get(2), MANNO3, NAME3, JOIN_DATE3, SEX3, ENROLLMENT_TYPE3, TIME_SAVING3);
	}

	@Test
	void testHelpEmployeeInfoToFormWhenHasNoData() {
		assertThat(employeeInfoHelper.helpEmployeeInfoToForm(Collections.emptyList()), is(Collections.emptyList()));
	}

	// data set
	private EmployeeInfoBean setEmployeeInfoBean(Integer manno, String name, Date joinDate, String sex, String enrollmentType, boolean timeSaving) {
		EmployeeInfoBean employeeInfoBean = new EmployeeInfoBean();
		employeeInfoBean.setManno(manno);
		employeeInfoBean.setName(name);
		employeeInfoBean.setJoinDate(joinDate);
		employeeInfoBean.setSex(sex);
		employeeInfoBean.setEnrollmentType(enrollmentType);
		employeeInfoBean.setTimeSaving(timeSaving);
		return employeeInfoBean;
	}

	/*
	 * assertion
	 * 第一引数にテスト実行結果のForm,第二引数以降に期待値を持たせ検証する
	 */
	private void assertEmployeeInfoHelper(EmployeeInfoForm form, Integer manno, String name, Date joinDate, String sex, String enrollmentType, boolean timeSaving) {
		assertThat(form.getManno(), is(manno));
		assertThat(form.getName(), is(name));
		assertThat(form.getJoinDate(), is(joinDate));
		// 性別がコード値から表示名称に変換されているか確認
		assertThat(form.getSex(), is(Const.SEX.get(Const.SEX.indexOf(sex)+1)));
		// 期待値の在籍区分コードから在籍区分名称の期待値を取得する
		String enrollmentValue;
		switch(enrollmentType) {
		case Const.ENROLLMENT_TYPE_ABSENCE:
			enrollmentValue = Const.EnumEnrollmentType.ABSENCE.getValue();
			break;
		case Const.ENROLLMENT_TYPE_RETIRE:
			enrollmentValue = Const.EnumEnrollmentType.RETIRE.getValue();
			break;
		default:
			enrollmentValue = Const.EnumEnrollmentType.INCUMBENT.getValue();
		}
		// 実行結果の在籍区分名称と比較検証する
		assertThat(form.getEnrollmentType(), is(enrollmentValue));
		assertThat(form.getTimeSaving(), is(timeSaving));
	}
}

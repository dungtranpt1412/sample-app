package jp.co.nttdmse.domain.service.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;
import jp.co.nttdmse.common.Const;
import jp.co.nttdmse.domain.entity.EmployeeInfo;
import jp.co.nttdmse.domain.repository.EmployeeInfoMapper;

@SpringBootTest
class EmployeeInfoServiceImplTest {

	@Mock
	EmployeeInfoMapper employeeInfoMapper;

	@Autowired
	@InjectMocks
	EmployeeInfoServiceImpl employeeService;

	// testData
	final Integer MANNO = 111111;
	final String NAME = "service test";
	final Date JOIN_DATE = Date.valueOf("2019-01-01");
	final String SEX = "F";
	final String ENROLLMENT_TYPE = "0";
	final boolean TIME_SAVING = false;

	/* 社員情報取得（テーブルにデータが1件以上存在する場合） */
	@Test
	void testGetEmployeeInfoListExistData() {
		// mocked
		when(employeeInfoMapper.selectByExample(any()))
			.thenReturn(Arrays.asList(mockEmployeeInfo(MANNO, NAME, JOIN_DATE, SEX, ENROLLMENT_TYPE, TIME_SAVING)));
		employeeService.employeeInfoMapper = employeeInfoMapper;

		// test
		List<EmployeeInfoBean> resultList = employeeService.getEmployeeInfoList();
		assertThat(resultList.size(), is(1));
		EmployeeInfoBean result = resultList.get(0);
		assertThat(result.getManno(), is(MANNO));
		assertThat(result.getName(), is(NAME));
		assertThat(result.getJoinDate(), is(JOIN_DATE));
		assertThat(result.getSex(), is(SEX));
		assertThat(result.getEnrollmentType(), is(ENROLLMENT_TYPE));
		assertThat(result.getTimeSaving(), is(TIME_SAVING));
	}

	/* 社員情報取得（テーブルにデータが存在しない場合） */
	@Test
	void testGetEmployeeInfoListNoData() {

		// mocked
		when(employeeInfoMapper.selectByExample(any())).thenReturn(Collections.emptyList());
		employeeService.employeeInfoMapper = employeeInfoMapper;

		// test
		assertThat(employeeService.getEmployeeInfoList().size(), is(0));
	}

	/* 社員情報登録正常処理 */
	@Test
	void testCreateEmployeeInfoSuccess() {

		// mocked
		when(employeeInfoMapper.insertSelective(any())).thenReturn(0);
		employeeService.employeeInfoMapper = employeeInfoMapper;

		// test
		assertThat(employeeService.createEmployeeInfo(new EmployeeInfoBean()), is(Const.CREATE_SUCCESS));
	}

	/* 社員情報登録エラー */
	@Test
	void testCreateEmployeeInfoError() {

		// mocked
		when(employeeInfoMapper.insertSelective(any())).thenThrow(DuplicateKeyException.class);
		employeeService.employeeInfoMapper = employeeInfoMapper;

		// test
		assertThat(employeeService.createEmployeeInfo(new EmployeeInfoBean()), is(Const.CREATE_ERROR));

	}

	/* make data */
	private EmployeeInfo mockEmployeeInfo(Integer manno, String name, Date joinDate, String sex, String enrollmentType, boolean timeSaving) {
		EmployeeInfo employeeInfo = new EmployeeInfo();
		employeeInfo.setManno(manno);
		employeeInfo.setName(name);
		employeeInfo.setJoinDate(joinDate);
		employeeInfo.setSex(sex);
		employeeInfo.setEnrollmentType(enrollmentType);
		employeeInfo.setTimeSaving(timeSaving);
		return employeeInfo;
	}
}

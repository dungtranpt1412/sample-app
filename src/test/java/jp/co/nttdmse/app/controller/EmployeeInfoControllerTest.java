package jp.co.nttdmse.app.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.nttdmse.app.form.EmployeeInfoForm;
import jp.co.nttdmse.app.helper.EmployeeInfoHelper;
import jp.co.nttdmse.common.CommonViewParts;
import jp.co.nttdmse.common.Const;
import jp.co.nttdmse.domain.service.impl.EmployeeInfoServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeInfoControllerTest {

	@Mock
	EmployeeInfoServiceImpl employeeService;

	@Mock
	EmployeeInfoHelper employeeInfoHelper;

	@Autowired
	@InjectMocks
	EmployeeInfoController employeeInfoController;

	@Autowired
	EmployeeInfoForm employeeInfoForm;

	private MockMvc mockMvc;

	private List<EmployeeInfoForm> formList;

	/* 複数テストケースで共通のMock生成 */
	@BeforeEach
	public void setup() {
		when(employeeService.getEmployeeInfoList()).thenReturn(Collections.emptyList());
		formList = Arrays.asList(mockHelpEmployeeInfoToForm());
		when(employeeInfoHelper.helpEmployeeInfoToForm(any())).thenReturn(formList);
		employeeInfoController.employeeService = employeeService;
		employeeInfoController.employeeInfoHelper = employeeInfoHelper;
		mockMvc = MockMvcBuilders.standaloneSetup(employeeInfoController).build();
	}

	/* 社員情報一覧画面表示 */
	@Test
	void testInit() throws Exception {
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("employeeInfoList"))
		.andExpect(model().attribute(Const.TITLE, Const.VIEW_EMPLOYEE_LIST))
		.andExpect(model().attribute("formList", formList));

	}

	/* 社員情報登録画面表示 */
	@Test
	void testCreate() throws Exception {
		mockMvc.perform(get("/create"))
		.andExpect(status().isOk())
		.andExpect(view().name("createEmployeeInfo"))
		.andExpect(model().attribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE))
		.andExpect(model().attribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap()))
		.andExpect(model().attribute("genderMap", CommonViewParts.getGenderMap()));
	}

	/* 社員情報登録バリデーションエラー */
	@Test
	void testCreateExecuteBindingError() throws Exception {
		EmployeeInfoForm createForm = new EmployeeInfoForm();
		mockMvc.perform(post("/createExecute").flashAttr("createForm", createForm))
		.andExpect(model().hasErrors())
		.andExpect(view().name("createEmployeeInfo"))
		.andExpect(model().attribute("createForm", createForm))
		.andExpect(model().attribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE))
		.andExpect(model().attribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap()))
		.andExpect(model().attribute("genderMap", CommonViewParts.getGenderMap()));
	}

	/* 社員情報登録時エラー */
	@Test
	void testCreateExecuteError() throws Exception {

		// mocked
		when(employeeService.createEmployeeInfo(any())).thenReturn(Const.CREATE_ERROR);
		employeeInfoController.employeeService = employeeService;
		mockMvc = MockMvcBuilders.standaloneSetup(employeeInfoController).build();

		EmployeeInfoForm createForm = mockHelpEmployeeInfoToForm();
		mockMvc.perform(post("/createExecute").flashAttr("createForm", createForm))
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("createEmployeeInfo"))
		.andExpect(model().attribute("createForm", createForm))
		.andExpect(model().attribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE))
		.andExpect(model().attribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap()))
		.andExpect(model().attribute("genderMap", CommonViewParts.getGenderMap()))
		.andExpect(model().attribute("error", Const.CREATE_ERROR));
	}

	/* 社員情報登録正常終了 */
	@Test
	void testCreateExecuteSuccess() throws Exception {

		// mocked
		when(employeeService.createEmployeeInfo(any())).thenReturn(Const.CREATE_SUCCESS);
		employeeInfoController.employeeService = employeeService;
		mockMvc = MockMvcBuilders.standaloneSetup(employeeInfoController).build();

		EmployeeInfoForm createForm = mockHelpEmployeeInfoToForm();
		mockMvc.perform(post("/createExecute").flashAttr("createForm", createForm))
		.andExpect(model().hasNoErrors())
		.andExpect(view().name("employeeInfoList"))
		.andExpect(model().attribute(Const.TITLE, Const.VIEW_EMPLOYEE_LIST))
		.andExpect(model().attribute("success", Const.CREATE_SUCCESS));
	}

	/* make form data */
	private EmployeeInfoForm mockHelpEmployeeInfoToForm(){
		employeeInfoForm.setManno(111111);
		employeeInfoForm.setName("test test");
		employeeInfoForm.setJoinDate(Date.valueOf("2020-01-01"));
		employeeInfoForm.setSex("女性");
		employeeInfoForm.setEnrollmentType("在職");
		employeeInfoForm.setTimeSaving(false);
		return employeeInfoForm;
	}
}

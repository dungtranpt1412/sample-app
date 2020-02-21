package jp.co.nttdmse.app.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.dozermapper.core.Mapper;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;
import jp.co.nttdmse.app.form.EmployeeInfoForm;
import jp.co.nttdmse.app.helper.EmployeeInfoHelper;
import jp.co.nttdmse.common.CommonViewParts;
import jp.co.nttdmse.common.Const;
import jp.co.nttdmse.domain.service.impl.EmployeeInfoServiceImpl;

@Controller
public class EmployeeInfoController {

	@Autowired
	EmployeeInfoServiceImpl employeeService;

	@Autowired
	EmployeeInfoHelper employeeInfoHelper;

	/* DozerMapping */
	@Autowired
	Mapper dozerMapper;

	/* 初期表示 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String init(Model model) {
		List<EmployeeInfoBean> empInfoBeanList = employeeService.getEmployeeInfoList();
		model.addAttribute("formList", employeeInfoHelper.helpEmployeeInfoToForm(empInfoBeanList));
		model.addAttribute(Const.TITLE, Const.VIEW_EMPLOYEE_LIST);
		return "employeeInfoList";
	}

	/* 新規登録画面表示 */
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE);
		model.addAttribute("createForm", new EmployeeInfoForm());
		model.addAttribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap());
		model.addAttribute("genderMap", CommonViewParts.getGenderMap());
		return "createEmployeeInfo";
	}

	/* 登録実行 */
	@RequestMapping(value="/createExecute", method=RequestMethod.POST)
	public String createExecute(Model model, @ModelAttribute("createForm") @Validated EmployeeInfoForm form, BindingResult bindingResult) {

		// 遷移先
		String retView = "employeeInfoList";

		// 入力チェックエラーの場合
		if(bindingResult.hasErrors()) {
			retView = "createEmployeeInfo";
			model.addAttribute("createForm", form);
			model.addAttribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE);
			model.addAttribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap());
			model.addAttribute("genderMap", CommonViewParts.getGenderMap());
			return retView;
		}

		/*
		 * DozerMapping
		 */
		EmployeeInfoBean createEmpInfoBean = new EmployeeInfoBean();
		dozerMapper.map(form, createEmpInfoBean);

		// createExecute
		String result = employeeService.createEmployeeInfo(createEmpInfoBean);

		// 登録失敗の場合は登録画面、登録完了の場合は一覧画面へ遷移する
		if(!Objects.equals(result, Const.CREATE_SUCCESS)) {
			retView = "createEmployeeInfo";
			model.addAttribute("createForm", form);
			model.addAttribute(Const.TITLE, Const.VIEW_EMPLOYEE_DATA_CREATE);
			model.addAttribute("enrollmentTypeMap", CommonViewParts.getEnrollmentTypeMap());
			model.addAttribute("genderMap", CommonViewParts.getGenderMap());
			model.addAttribute("error", result);
		} else {
			List<EmployeeInfoBean> empInfoBeanList = employeeService.getEmployeeInfoList();
			model.addAttribute("formList", employeeInfoHelper.helpEmployeeInfoToForm(empInfoBeanList));
			model.addAttribute(Const.TITLE, Const.VIEW_EMPLOYEE_LIST);
			model.addAttribute("success", result);
		}

		return retView;
	}
}

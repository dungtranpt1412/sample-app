package jp.co.nttdmse.app.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;
import jp.co.nttdmse.app.form.EmployeeInfoForm;
import jp.co.nttdmse.common.Const;

@Component
public class EmployeeInfoHelper {

	public List<EmployeeInfoForm> helpEmployeeInfoToForm(List<EmployeeInfoBean> empInfoBeanList){

		// 引数が空のリストの場合は空のリストを返す
		if(empInfoBeanList.isEmpty()) {
			return Collections.emptyList();
		}

		List<EmployeeInfoForm> retList = new ArrayList<>();
		for(EmployeeInfoBean empInfoBean : empInfoBeanList) {

			EmployeeInfoForm empForm = new EmployeeInfoForm();
			empForm.setManno(empInfoBean.getManno());
			empForm.setName(empInfoBean.getName());
			empForm.setJoinDate(empInfoBean.getJoinDate());

			int index = Const.SEX.indexOf(empInfoBean.getSex()) + 1;
			empForm.setSex(Const.SEX.get(index));

			switch(empInfoBean.getEnrollmentType()) {
			case Const.ENROLLMENT_TYPE_ABSENCE:
				empForm.setEnrollmentType(Const.EnumEnrollmentType.ABSENCE.getValue());
				break;
			case Const.ENROLLMENT_TYPE_RETIRE:
				empForm.setEnrollmentType(Const.EnumEnrollmentType.RETIRE.getValue());
				break;
			default:
				empForm.setEnrollmentType(Const.EnumEnrollmentType.INCUMBENT.getValue());
			}
			empForm.setTimeSaving(empInfoBean.getTimeSaving());
			retList.add(empForm);
		}
		return retList;
	}

}

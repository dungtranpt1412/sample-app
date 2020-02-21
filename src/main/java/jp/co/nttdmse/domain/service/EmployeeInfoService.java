package jp.co.nttdmse.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;

@Service
public interface EmployeeInfoService {

	/* 社員一覧検索 */
	public List<EmployeeInfoBean> getEmployeeInfoList();

	/* 新規登録 */
	public String createEmployeeInfo(EmployeeInfoBean bean);

}

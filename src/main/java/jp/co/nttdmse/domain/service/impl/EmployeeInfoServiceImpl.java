package jp.co.nttdmse.domain.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.github.dozermapper.core.Mapper;

import jp.co.nttdmse.app.bean.EmployeeInfoBean;
import jp.co.nttdmse.common.Const;
import jp.co.nttdmse.domain.entity.EmployeeInfo;
import jp.co.nttdmse.domain.entity.EmployeeInfoExample;
import jp.co.nttdmse.domain.logic.SampleLogic;
import jp.co.nttdmse.domain.repository.EmployeeInfoMapper;
import jp.co.nttdmse.domain.service.EmployeeInfoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeInfoServiceImpl implements EmployeeInfoService {

	@Autowired
	EmployeeInfoExample employeeInfoExample;

	@Autowired
	EmployeeInfoMapper employeeInfoMapper;

	@Autowired
	SampleLogic sampleLogic;

	/* DozerMapping */
	@Autowired
	Mapper dozerMapper;

	@Override
	public List<EmployeeInfoBean> getEmployeeInfoList(){

		// log
		log.info("getEmployeeInfoList Start!");

		employeeInfoExample.createCriteria().andJoinDateBetween(Date.valueOf("2019-07-01"), Date.valueOf("2020-02-01"));
		employeeInfoExample.setOrderByClause("manno DESC");
		List<EmployeeInfo> employeeInfoList = employeeInfoMapper.selectByExample(employeeInfoExample);

		// 0件の場合は空のリストを返す
		if(Objects.equals(employeeInfoList.size(), 0)) {
			return Collections.emptyList();
		}

		List<EmployeeInfoBean> retList = new ArrayList<>();
		for(EmployeeInfo empInfo : employeeInfoList) {
			/*
			 * DozerMapping
			 */
			EmployeeInfoBean empInfoBean = new EmployeeInfoBean();
			dozerMapper.map(empInfo, empInfoBean);
			retList.add(empInfoBean);
		}

		// Logicクラス呼び出しサンプルコード
		sampleLogic.doSampleLogic(new Object(){}.getClass().getEnclosingClass().getName());

		return retList;

	}

	@Override
	public String createEmployeeInfo(EmployeeInfoBean insertEmpInfoBean) {

		// log
		log.info("createEmployeeInfo start!");

		/*
		 * DozerMapping
		 */
		EmployeeInfo insertEmpInfo = new EmployeeInfo();
		dozerMapper.map(insertEmpInfoBean, insertEmpInfo);

		// TODO エラーハンドリングは仮実装
		try {
			employeeInfoMapper.insertSelective(insertEmpInfo);
		}catch(DataAccessException e) {
			e.printStackTrace();
			return Const.CREATE_ERROR;
		}
		return Const.CREATE_SUCCESS;
	}

}

package jp.co.nttdmse.common;

import java.util.Arrays;
import java.util.List;

public class Const {

	public static enum EnumEnrollmentType{

		INCUMBENT("在職"),
		ABSENCE("休職"),
		RETIRE("退職");

		private String enrollmentType;

		EnumEnrollmentType(String enrollmentType) {
			this.enrollmentType = enrollmentType;
		}

		public String getValue() {
			return enrollmentType;
		}
	}

	public static final List<String> SEX = Arrays.asList("M", "男性", "F", "女性");

	public static final String ENROLLMENT_TYPE_INCUMBENT = "0";
	public static final String ENROLLMENT_TYPE_ABSENCE = "1";
	public static final String ENROLLMENT_TYPE_RETIRE = "9";

	public static final String CREATE_SUCCESS = "登録しました！";
	public static final String CREATE_ERROR = "登録に失敗しました。入力内容を確認してください";

	public static final String VIEW_EMPLOYEE_LIST = "社員情報一覧";
	public static final String VIEW_EMPLOYEE_DATA_CREATE = "社員情報登録";
	public static final String TITLE = "title";
	public static final String VIEW_MASTER_REGISTER = "マスタ登録";
}

package jp.co.nttdmse.app.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class EmployeeInfoBean {

    private Integer manno;

    private String name;

    private Date joinDate;

    private String sex;

    private String enrollmentType;

    private Boolean timeSaving;

}

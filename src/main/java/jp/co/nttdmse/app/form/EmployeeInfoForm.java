package jp.co.nttdmse.app.form;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jp.co.nttdmse.common.validation.annotation.ManNo;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class EmployeeInfoForm {

	@NotNull
	@ManNo
    private Integer manno;

	@NotNull
	@NotBlank
	@Size(max = 20)
    private String name;

	@NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date joinDate;

	@NotNull
    private String sex;

	@NotNull
    private String enrollmentType;

	@NotNull
    private Boolean timeSaving;
}

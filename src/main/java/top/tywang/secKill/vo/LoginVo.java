package top.tywang.secKill.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import top.tywang.secKill.validator.IsMobile;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

}

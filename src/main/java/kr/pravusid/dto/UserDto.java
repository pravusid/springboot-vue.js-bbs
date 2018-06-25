package kr.pravusid.dto;

import javax.validation.constraints.Size;

import kr.pravusid.domain.user.User;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.pravusid.util.FieldsMatcher;

@FieldsMatcher(baseField = "password", matchField = "confirmpassword", message = "비밀번호가 일치하지 않습니다")
public class UserDto extends BaseDto {

    @Size(min = 4, message = "아이디는 4글자 이상 입력해주세요")
    private String username;

    @Size(min = 4, message = "비밀번호는 4글자 이상 입력해주세요")
    private String password;
    private String confirmpassword;

    @Size(min = 4, message = "이름은 4글자 이상 입력해주세요")
    private String name;

    @NotBlank(message = "이메일 주소를 입력해주세요")
    @Email(message = "올바른 이메일주소가 아닙니다")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toEntity() {
        return new User(username, password, name, email);
    }

}

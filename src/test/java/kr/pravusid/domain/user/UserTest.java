package kr.pravusid.domain.user;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private User user = new User("tester", "1234", "테스터", "test@kr");

    @Test
    public void 생성하면_USER_권한을_획득한상태이다() {
        assertThat(user.getAuthorities().contains(Authority.USER)).isTrue();
    }

    @Test
    public void 평문_비밀번호를_입력하면_BCrypt_암호화_처리가_되어있다() {
        // WHEN
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // THEN
        assertThat(encoder.matches("1234", user.getPassword())).isTrue();
    }

    @Test
    public void 회원_정보를_수정한다() {
        // GIVEN
        User origin = new User("tester", "1234", "테스터", "test@kr");
        User request = new User("new", "4321", "수정", "new@kr");

        // WHEN
        origin.update(request);

        // THEN
        // 수정불가능
        assertThat(origin.getUsername()).isNotEqualTo(request.getUsername());
        // 수정가능
        assertThat(origin.getPassword()).isEqualTo(request.getPassword());
        assertThat(origin.getName()).isEqualTo(request.getName());
        assertThat(origin.getEmail()).isEqualTo(request.getEmail());
    }

    @Test
    public void 동일회원인지_확인한다() {
        assertThat(user.verifyUser("tester")).isTrue();
    }

}

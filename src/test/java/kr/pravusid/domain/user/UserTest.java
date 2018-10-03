package kr.pravusid.domain.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTest {

    private User user;

    @Before
    public void 회원_객체를_생성한다() {
        user = new User("tester", "1234", "테스터", "test@kr");
    }

    @Test
    public void 생성하면_USER_권한을_획득한상태이다() {
        Assert.assertTrue(user.getAuthorities().contains(Authority.USER));
    }

    @Test
    public void 평문_비밀번호를_입력하면_BCrypt_암호화_처리가_되어있다() {
        // WHEN
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // THEN
        Assert.assertTrue(encoder.matches("1234", user.getPassword()));
    }

    @Test
    public void 회원_정보를_수정한다() {
        // GIVEN
        User origin = new User("tester", "1234", "테스터", "test@kr");
        User request = new User("new", "4321", "수정", "new@kr");

        // WHEN
        origin.update(request);

        // THEN
        Assert.assertNotEquals(request.getUsername(), origin.getUsername());
        Assert.assertEquals(request.getPassword(), origin.getPassword());
        Assert.assertEquals(request.getName(), origin.getName());
        Assert.assertEquals(request.getEmail(), origin.getEmail());
    }

    @Test
    public void 동일회원인지_확인한다() {
        Assert.assertTrue(user.verifyUser("tester"));
    }

}

package kr.pravusid.domain.user;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void 반복_회원을_추가한다() {
        User user = new User("tester", "1234", "테스터", "test@kr");
        userRepository.save(user);
    }

    @After
    public void 반복_회원을_삭제한다() {
        userRepository.deleteAll();
    }

    @Test
    public void Username으로_회원을_찾는다() {
        // WHEN
        User user = userRepository.findByUsername("tester");

        // THEN
        assertThat(user.getEmail()).isEqualTo("test@kr");
    }

    @Test
    public void 잘못된_Username은_빈결과를_반환한다() {
        // WHEN
        User user = userRepository.findByUsername("admin");

        // THEN
        assertThat(user).isNull();
    }

    @Test
    public void Email으로_회원을_찾는다() {
        // WHEN
        User user = userRepository.findByEmail("test@kr");

        // THEN
        assertThat(user.getUsername()).isEqualTo("tester");
    }

    @Test
    public void 잘못된_Email은_빈결과를_반환한다() {
        // WHEN
        User user = userRepository.findByEmail("admin@kr");

        // THEN
        assertThat(user).isNull();
    }

}

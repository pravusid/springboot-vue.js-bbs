package kr.pravusid.domain.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void 회원을_DB에_저장한다() {
        User user = new User("tester", "1234", "테스터", "test@kr");
        userRepository.save(user);
    }

    @Test
    public void Username으로_회원을_찾는다() {
        // WHEN
        User user = userRepository.findByUsername("tester");

        // THEN
        Assert.assertEquals("test@kr", user.getEmail());
    }

    @Test
    public void 잘못된_Username은_빈결과를_반환한다() {
        // WHEN
        User user = userRepository.findByUsername("admin");

        // THEN
        Assert.assertNull(user);
    }

    @Test
    public void Email으로_회원을_찾는다() {
        // WHEN
        User user = userRepository.findByEmail("test@kr");

        // THEN
        Assert.assertEquals("tester", user.getUsername());
    }

    @Test
    public void 잘못된_Email은_빈결과를_반환한다() {
        // WHEN
        User user = userRepository.findByEmail("admin@kr");

        // THEN
        Assert.assertNull(user);
    }

}

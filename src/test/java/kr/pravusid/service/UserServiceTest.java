package kr.pravusid.service;

import kr.pravusid.domain.user.User;
import kr.pravusid.domain.user.UserRepository;
import kr.pravusid.dto.UserDto;
import kr.pravusid.dto.exception.CustomValidationException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @After
    public void 기본회원_이외의_회원을_삭제한다() {
        userRepository.findAll()
                .stream()
                .filter(u -> !u.getUsername().equals("user"))
                .forEach(u -> userRepository.delete(u.getId()));
    }

    private UserDto 요청을_생성한다() {
        UserDto dto = new UserDto();
        dto.setUsername("hogu");
        dto.setPassword("1234");
        dto.setConfirmpassword("1234");
        dto.setName("호구");
        dto.setEmail("test@kr");
        return dto;
    }

    @Test
    public void 요청한_Username의_UserDetails_반환() {
        // WHEN
        UserDetails user = userService.loadUserByUsername("user");

        // THEN
        assertEquals("user", user.getUsername());
    }

    @Test
    public void 중복되는_정보가_없다면_회원을_등록한다() {
        // GIVEN
        UserDto dto = 요청을_생성한다();

        // WHEN
        User user = userService.save(dto);

        // THEN
        User result = userRepository.findOne(user.getId());
        assertEquals("hogu", result.getUsername());
        assertEquals("test@kr", result.getEmail());
    }

    @Test
    public void 중복되는_Username이_있다면_회원등록중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        dto.setUsername("user");

        // WHEN THEN
        try {
            userService.save(dto);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof CustomValidationException);
        }
    }

    @Test
    public void 중복되는_Email이_있다면_회원등록중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        dto.setEmail("test@com");

        // WHEN THEN
        try {
            userService.save(dto);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof CustomValidationException);
        }
    }

    @Test
    public void 회원정보를_수정한다() {
        // GIVEN
        User user = userRepository.findByUsername("user");
        UserDto dto = UserDto.of(user);
        dto.setUsername("userAnte");
        dto.setPassword("1234");
        dto.setName("참새");
        dto.setEmail("test@jp");

        // WHEN
        User userPost = userService.update(dto);

        // THEN
        User result = userRepository.findOne(userPost.getId());
        assertEquals("tester", user.getName());
        assertEquals("test@com", user.getEmail());
        assertEquals("참새", result.getName());
        assertEquals("test@jp", result.getEmail());
        assertNotEquals("userAnte", result.getUsername());
    }

    @Test
    public void 중복되는_Email이_있다면_정보수정중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        userRepository.save(dto.toEntity());

        User user = userRepository.findByUsername("user");
        UserDto request = UserDto.of(user);
        request.setEmail(dto.getEmail());

        // WHEN THEN
        try {
            userService.update(request);
        } catch (Exception e) {
            assertTrue(e instanceof CustomValidationException);
        }
    }

    @Test
    public void 동일_회원인지_확인한다() {
        // GIVEN
        User user = userRepository.findByUsername("user");

        // WHEN THEN
        assertTrue(user.verifyUser("user"));
    }

}

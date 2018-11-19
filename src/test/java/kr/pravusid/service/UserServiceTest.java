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

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void 요청한_Username의_UserDetails_반환() {
        // WHEN
        UserDetails user = userService.loadUserByUsername("user");

        // THEN
        assertThat(user.getUsername()).isEqualTo("user");
    }

    @Test
    public void 중복되는_정보가_없다면_회원을_등록한다() {
        // GIVEN
        UserDto dto = 요청을_생성한다();

        // WHEN
        User user = userService.save(dto);

        // THEN
        User result = userRepository.findOne(user.getId());
        assertThat(result.getUsername()).isEqualTo("hogu");
        assertThat(result.getEmail()).isEqualTo("test@kr");
    }

    @Test(expected = CustomValidationException.class)
    public void 중복되는_Username이_있다면_회원등록중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        dto.setUsername("user");

        // WHEN THEN
        userService.save(dto);
    }

    @Test(expected = CustomValidationException.class)
    public void 중복되는_Email이_있다면_회원등록중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        dto.setEmail("test@com");

        // WHEN THEN
        userService.save(dto);
    }

    @Test
    public void 회원정보를_수정한다() {
        // GIVEN
        User user = userRepository.findByUsername("user");
        UserDto dto = UserDto.of(user);
        // 수정불가
        dto.setUsername("userAnte");
        // 수정가능
        dto.setPassword("1234");
        dto.setName("참새");
        dto.setEmail("test@jp");

        // WHEN
        User userPost = userService.update(dto);

        // THEN
        User result = userRepository.findOne(userPost.getId());
        assertThat(user.getEmail()).isEqualTo("test@com");
        assertThat(user.getUsername()).isEqualTo("user");

        assertThat(result.getEmail()).isEqualTo("test@jp");
        assertThat(result.getUsername()).isEqualTo("user");
    }

    @Test(expected = CustomValidationException.class)
    public void 중복되는_Email이_있다면_정보수정중_예외발생() {
        // GIVEN
        UserDto dto = 요청을_생성한다();
        userRepository.save(dto.toEntity());

        User user = userRepository.findByUsername("user");
        UserDto request = UserDto.of(user);
        request.setEmail(dto.getEmail());

        // WHEN THEN
        userService.update(request);
    }

    @Test
    public void 동일_회원인지_확인한다() {
        // GIVEN
        User user = userRepository.findByUsername("user");

        // WHEN THEN
        assertThat(user.verifyUser("user")).isTrue();
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

}

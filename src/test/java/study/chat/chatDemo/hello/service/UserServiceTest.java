package study.chat.chatDemo.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Slf4j
@Rollback
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;


    @Test
    public void showMacroFromApiServerByUserId() {
        String result = null;

        log.debug("case1");
        result=userService.showMacroFromApiServerByUserId("2");
        assertNotNull(result);
        assertThat(result, is("user2"));
    }
}
package study.chat.chatDemo.external.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import study.chat.chatDemo.external.dto.GetMacroRequest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Slf4j
@Rollback
@SpringBootTest
public class ApiServerCallServiceTest {

    @Autowired
    private ApiServerCallService apiServerCallService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getMacroContents() {
        String result = apiServerCallService.getMacroContents(
                GetMacroRequest.builder().macroContentsNo(3).build()
        );

        assertNotNull(result);
    }
}
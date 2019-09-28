package study.chat.chatDemo.hello.service;

import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
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
public class ContentsSeviceTest {

    @Autowired
    private ContentsSevice contentsSevice;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void showMacroFromApiServer() {
    }

    @Test
    public void showMacroFromApiServerByContentsNo() {
        String result=null;

        log.debug("case 1");
        result = contentsSevice.showMacroFromApiServerByContentsNo(3);
        assertNotNull(result);
        assertThat(result, is("공부 좀 열심히 해라"));

        log.debug("case 2");
        result = contentsSevice.showMacroFromApiServerByContentsNo(2);
        assertNotNull(result);
        assertThat(result, is("study works!"));

        log.debug("case 3");
        result = contentsSevice.showMacroFromApiServerByContentsNo(4);
        assertNull(result);
    }
}
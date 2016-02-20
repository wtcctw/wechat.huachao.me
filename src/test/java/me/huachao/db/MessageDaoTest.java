package me.huachao.db;

import me.huachao.dto.message.input.TextInputMessage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by huachao on 2/1/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/appcontext-*.xml"})
public class MessageDaoTest {

    @Resource
    private MessageDao messageDao;

    @Test
    public void testCreate() {
        Assert.assertNotNull("asdf");
    }

    @Test
    public void testInsert() {
        TextInputMessage inputMessage = new TextInputMessage("to", "from", "123", "type", "234", "content2");
        Assert.assertTrue(messageDao.insert(inputMessage)!=0);
    }
}

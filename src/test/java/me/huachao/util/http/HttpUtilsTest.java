package me.huachao.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by huachao on 1/28/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/appcontext-server.xml"})
public class HttpUtilsTest {

    @Resource
    private HttpUtils httpUtils;

    @Test
    public void testGetResponseT() {
        String body = httpUtils.getResponse("http://sandbox.api.simsimi.com/request.p?key=4c353134-3d6d-4da9-bb15-a189065e44e4&lc=ch&text=你好啊",
                new ResponseHandler<String>() {
                    public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                            HttpEntity entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                    }
                }
        );
        Assert.assertNotNull(body);
    }

}

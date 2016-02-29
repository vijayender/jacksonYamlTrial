package com.kvijayender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by vkarnaty on 2/29/16.
 */
public class YamlSimplePOJOBindingTest {
    @Test
    public void testPOJOReadYamlClassAndPrint() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        //User user = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.yaml"), User.class);
        User user = User.getUser();
        User user2 = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.yaml"), User.class);
        assert(user.equals(user2));
    }
}

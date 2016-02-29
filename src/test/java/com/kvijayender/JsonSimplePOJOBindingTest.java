package com.kvijayender;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

public class JsonSimplePOJOBindingTest {
    String FILE_PATH = "/tmp/test_POJO_";

    @Test
    public void testSimplePOJOMarshallUnmarshall() throws IOException {
        String fpath = FILE_PATH + "user.json";
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.json"), User.class);
        User user2 = User.getUser();

        assert(user.equals(user2));

        File resultFile = new File(fpath);
        mapper.writeValue(resultFile, user2);
        mapper.readValue(resultFile, User.class).equals(user);
    }

    @Test
    public void testReadGeneric() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapped = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.json"), Map.class);
        Map<String, String> name = (Map<String, String>) mapped.get("name");
        User user = User.getUser();
        Assert.assertEquals(name.get("first"), user.getName().getFirst());
        Assert.assertEquals(name.get("last"), user.getName().getLast());
        Assert.assertEquals(mapped.get("userImage"), new String(Base64.getEncoder().encode(user.getUserImage())));
    }
}

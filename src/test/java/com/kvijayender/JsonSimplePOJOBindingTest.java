package com.kvijayender;

import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;

/**
 * Created by vijayender on 2/29/16.
 */
public class JsonSimplePOJOBindingTest {
    String FILE_PATH = "/tmp/test_POJO_";

    public static class User {
        public enum Gender { MALE, FEMALE };

        public static class Name {
            private String _first, _last;

            public String getFirst() { return _first; }
            public String getLast() { return _last; }

            public void setFirst(String s) { _first = s; }
            public void setLast(String s) { _last = s; }

            public boolean equals(Name obj) {
                return _first.equals(obj._first) && _last.equals(obj._last);
            }
        }

        private Gender _gender;
        private Name _name;
        private boolean _isVerified;
        private byte[] _userImage;

        public Name getName() { return _name; }
        public boolean isVerified() { return _isVerified; }
        public Gender getGender() { return _gender; }
        public byte[] getUserImage() { return _userImage; }

        public void setName(Name n) { _name = n; }
        public void setVerified(boolean b) { _isVerified = b; }
        public void setGender(Gender g) { _gender = g; }
        public void setUserImage(byte[] b) { _userImage = b; }

        public boolean equals(User obj) {
            return _name.equals(obj.getName())
                    // Note that byte arrays are encoded decoded automatically to/from Base64 when outputting to json
                    && Arrays.equals(_userImage, obj.getUserImage())
                    && _isVerified == obj.isVerified()
                    && _gender == obj.getGender();
        }
    }

    @Test
    public void testSimplePOJOMarshallUnmarshall() throws IOException {
        String fpath = FILE_PATH + "user.json";
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.json"), User.class);
        User user2 = getUser();

        assert(user.equals(user2));

        File resultFile = new File(fpath);
        mapper.writeValue(resultFile, user2);
        mapper.readValue(resultFile, User.class).equals(user);
    }

    private User getUser() {
        User.Name name = new User.Name();
        name.setFirst("Joe");
        name.setLast("Sixpack");
        User user2 = new User();
        user2.setName(name);
        user2.setGender(User.Gender.MALE);
        user2.setVerified(false);
        user2.setUserImage(Base64.getDecoder().decode("Rm9vYmFyIQ==".getBytes()));
        return user2;
    }

    @Test
    public void testReadGeneric() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapped = mapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("user.json"), Map.class);
        Map<String, String> name = (Map<String, String>) mapped.get("name");
        User user = getUser();
        Assert.assertEquals(name.get("first"), user.getName()._first);
        Assert.assertEquals(name.get("last"), user.getName()._last);
        Assert.assertEquals(mapped.get("userImage"), new String(Base64.getEncoder().encode(user.getUserImage())));
    }
}

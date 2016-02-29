package com.kvijayender;

import java.util.Arrays;
import java.util.Base64;

/**
 * Created by vijayender on 2/29/16.
 */

public class User {
    static User getUser() {
        Name name = new Name();
        name.setFirst("Joe");
        name.setLast("Sixpack");
        User user2 = new User();
        user2.setName(name);
        user2.setGender(Gender.MALE);
        user2.setVerified(false);
        user2.setUserImage(Base64.getDecoder().decode("Rm9vYmFyIQ==".getBytes()));
        return user2;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "_gender=" + _gender +
                ", _name=" + _name +
                ", _isVerified=" + _isVerified +
                ", _userImage=" + Arrays.toString(_userImage) +
                '}';
    }
}

package com.crw.java8.other;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Demo {

    public static void main(String[] args) {
        final String text = "测试Abc123!!￥￥";
        final String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);
        final String decoded = new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
        System.out.println(decoded);

        // url encode
        final String url = "http://www.jinhui365.com/abc?foo=中文&￥%&bar=hello123&baz=http://abc/def123";
        final String encoded2 = Base64.getUrlEncoder().encodeToString(url.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded2);
        final String decoded2 = new String(Base64.getUrlDecoder().decode(encoded2), StandardCharsets.UTF_8);
        System.out.println(decoded2);
    }
}

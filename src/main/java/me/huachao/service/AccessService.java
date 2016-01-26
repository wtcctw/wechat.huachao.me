package me.huachao.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by huachao on 1/26/16.
 */
public class AccessService {

    private static final Logger logger = LoggerFactory.getLogger(AccessService.class);

    /**
     * 微信开发者模式接入验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param token
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce, String token) {
        List<String> tmp = Arrays.asList(token, timestamp, nonce);
        Collections.sort(tmp, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String all = "";
        for (String s : tmp) {
            all += s;
        }

        String sha1Hex = DigestUtils.sha1Hex(all);
        logger.info(String.format("origin:%s sha1:%s", all, sha1Hex));

        if ( signature.equals(sha1Hex) ) {
            return true;
        }
        return false;
    }
}

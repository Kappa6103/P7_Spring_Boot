package com.nnk.springboot.integration;

import com.nnk.springboot.constant.Const;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Khang Nguyen.
 * Email: khang.nguyen@banvien.com
 * Date: 09/03/2019
 * Time: 11:26 AM
 */

@SpringBootTest
public class PasswordEncodeIT {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testPassword() {
        String pw = passwordEncoder.encode("123456");
        System.out.println("[ "+ pw + " ]");
    }

    @Test
    void testPassword_sizeConsistency() {
        for (int i = 0; i < 100; i++) {
            String pwd = passwordEncoder.encode(Const.PWD + i);
            assertEquals(Const.PWD_HASHED_SIZE, pwd.length());
        }
    }
}

package com.example.quizizz.password;

import lombok.Data;

/**
 * @author Sampson Alfred
 */
@Data
public class PasswordRequestUtil {
    private String username;
    private String oldPassword;
    private String newPassword;
}

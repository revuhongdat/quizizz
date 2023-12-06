package com.example.quizizz.DTO;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
	private String email;
    private String newPassword;
    public String getToken;
}

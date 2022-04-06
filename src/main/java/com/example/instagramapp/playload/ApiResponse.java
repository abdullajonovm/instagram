package com.example.instagramapp.playload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private Boolean success;
    private Object object;

    public ApiResponse(String message, Boolean success){
        this.message=message;
        this.success=success;
    }
}

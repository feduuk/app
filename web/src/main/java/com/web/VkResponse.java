package com.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkResponse {
    private String access_token;
    private String expires_in;
    private String user_id;
}

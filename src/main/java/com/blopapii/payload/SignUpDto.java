package com.blopapii.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private long id;

    private String name;

    private String username;

    private String email;

    private String password;
}

package com.blopapii.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {


    private long id;

    @NotEmpty(message="title should not be empty")
    private String title;

    @NotEmpty(message="description should not be empty")
    @Size(min=2,message="description should be more than 2 character")
    private String description;

    @NotEmpty(message = "content should not be empty")
    @Size(min=2,message="content should be more than 2 character")
    private String content;
}

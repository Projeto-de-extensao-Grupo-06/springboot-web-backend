package com.solarize.solarizeWebBackend.modules.projectComment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectCommentRequestDTO {

    @NotNull(message = "The author ID is  required")
    private Long authorId;

    @NotNull(message = "The project ID is required")
    private Long projectId;

    @NotBlank(message = "The comment can´t be blank")
    @Size(max = 5000, message = "The comment exceed the limit of characters")
    private String comment;


}

package com.zelalem.user.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
    @NotNull(message = "userId (username/email) must be specified!")
    @JsonProperty("userId")
    private String userId;
}

package seeNear.seeNear_BE.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSignUpTokenDto {
    private String signUpToken;
}

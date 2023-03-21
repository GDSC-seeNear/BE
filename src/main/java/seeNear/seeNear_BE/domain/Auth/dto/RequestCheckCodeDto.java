package seeNear.seeNear_BE.domain.Auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestCheckCodeDto {
    private String phoneNumber;
    private String certificationNumber;

}

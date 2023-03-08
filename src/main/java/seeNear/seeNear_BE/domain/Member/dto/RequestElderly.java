package seeNear.seeNear_BE.domain.Member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestElderly {
    private int id;
    private String phoneNumber;
    private String name;
    private Date birth;
    private double addressLati;
    private double addressLongi;
    private String addressDetail;
    private Boolean isConnect;
    private Integer guardianId;
}

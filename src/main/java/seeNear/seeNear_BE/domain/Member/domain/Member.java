package seeNear.seeNear_BE.domain.Member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
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

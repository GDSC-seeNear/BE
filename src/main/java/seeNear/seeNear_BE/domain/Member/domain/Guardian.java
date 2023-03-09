package seeNear.seeNear_BE.domain.Member.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class Guardian implements Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String phoneNumber;
    private String name;


    public Guardian(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}

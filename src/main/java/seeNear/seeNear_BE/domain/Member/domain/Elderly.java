package seeNear.seeNear_BE.domain.Member.domain;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@TypeDef(name = "json", typeClass = JsonType.class)
public class Elderly implements Member{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNumber;
    private String name;

    private LocalDate birth;
    private double addressLati;
    private double addressLongi;
    private String addressDetail;
    private Boolean isConnect;
    private Integer guardianId;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private List<String> emergencyPhoneNumber;

    public Elderly(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    public String getBirth() {
        if (birth == null) {
            return null;
        }
        return birth.toString();
    }
}

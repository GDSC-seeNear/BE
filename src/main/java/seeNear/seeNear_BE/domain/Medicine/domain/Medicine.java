package seeNear.seeNear_BE.domain.Medicine.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int elderlyId;
    private int period;

    public Medicine(String name, int elderlyId, int period) {
        this.name = name;
        this.elderlyId = elderlyId;
        this.period = period;
    }

}

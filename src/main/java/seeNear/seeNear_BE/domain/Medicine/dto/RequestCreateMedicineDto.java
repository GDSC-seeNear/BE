package seeNear.seeNear_BE.domain.Medicine.dto;

import lombok.Data;

@Data
public class RequestCreateMedicineDto {
    private String name;
    private int elderlyId;
    private int period;
}

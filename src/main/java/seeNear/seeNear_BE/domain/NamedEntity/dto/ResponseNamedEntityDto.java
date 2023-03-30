package seeNear.seeNear_BE.domain.NamedEntity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseNamedEntityDto {
    private int id;
    private String type;
    private String target;
    private LocalDateTime createdAt;
    private String content;

    public String getCreatedAt() {
        return createdAt.toString();
    }

}

package seeNear.seeNear_BE.domain.NamedEntity.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseNamedEntityListDto {
    List<ResponseNamedEntityDto> nerList;
}

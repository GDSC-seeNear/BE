package seeNear.seeNear_BE.domain.Report.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import seeNear.seeNear_BE.domain.NamedEntity.dto.ResponseNamedEntityDto;

import seeNear.seeNear_BE.domain.StatusCheck.domain.Status;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Report {
    String date;
    List<Status> statusList;
    List<ResponseNamedEntityDto> nerList;
    Map<String,Long> sentimentList;

}

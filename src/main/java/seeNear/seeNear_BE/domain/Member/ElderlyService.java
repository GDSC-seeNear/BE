package seeNear.seeNear_BE.domain.Member;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.dto.RequestElderly;
import seeNear.seeNear_BE.exception.CustomException;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_AUTHORITY;
import static seeNear.seeNear_BE.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
public class ElderlyService {

    private ElderlyRepository elderlyRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public ElderlyService(ElderlyRepository elderlyRepository) {
        this.elderlyRepository = elderlyRepository;
    }

    public Elderly getElderly(int id) {
        Elderly elderly = elderlyRepository.findById(id);
        if (elderly == null) {
            throw new CustomException(MEMBER_NOT_FOUND, String.format("request id: %d", id));
        }
        return elderly;
    }

    public Elderly update(RequestElderly updateInfo, int elderlyId) {
        if (updateInfo.getId() != elderlyId) {
            throw new CustomException(INVALID_AUTHORITY, String.format("user id: %d , request id: %d", elderlyId, updateInfo.getId()));
        }
        //이게 필요할까?
        if (elderlyRepository.findById(updateInfo.getId()) == null) {
            throw new CustomException(MEMBER_NOT_FOUND, String.format("request id: %d", updateInfo.getId()));
        }
        Elderly elderly = modelMapper.map(updateInfo, Elderly.class);

        return elderlyRepository.update(elderly);
    }
}

package seeNear.seeNear_BE.domain.Member;

import org.springframework.stereotype.Service;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;

@Service
public class ElderlyService {

    private ElderlyRepository elderlyRepository;
    public ElderlyService(ElderlyRepository elderlyRepository){
        this.elderlyRepository = elderlyRepository;
    }

    public Elderly update (Elderly updateInfo) {
        return elderlyRepository.update(updateInfo);
    }
}

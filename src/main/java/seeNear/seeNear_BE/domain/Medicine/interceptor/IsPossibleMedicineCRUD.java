package seeNear.seeNear_BE.domain.Medicine.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import seeNear.seeNear_BE.domain.Medicine.MedicineRepository;
import seeNear.seeNear_BE.domain.Medicine.domain.Medicine;
import seeNear.seeNear_BE.domain.Medicine.dto.RequestCreateMedicineDto;
import seeNear.seeNear_BE.domain.Member.ElderlyRepository;
import seeNear.seeNear_BE.domain.Member.MemberEnum.Role;
import seeNear.seeNear_BE.domain.Member.domain.Elderly;
import seeNear.seeNear_BE.domain.Member.domain.Member;
import seeNear.seeNear_BE.exception.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Set;

import static seeNear.seeNear_BE.exception.ErrorCode.INVALID_AUTHORITY;
import static seeNear.seeNear_BE.exception.ErrorCode.MISMATCH_INFO;

public class IsPossibleMedicineCRUD implements HandlerInterceptor {
    @Autowired
    private ElderlyRepository elderlyRepository;
    @Autowired
    private MedicineRepository medicineRepository;
    @Autowired
    private ObjectMapper objectMapper;

    public boolean validateUri(String requestUri) {
        Set<String> fixed = Set.of("create", "getByElderlyId", "update", "delete");
        String[] uri = requestUri.split("/");
        if (uri.length < 3) {
            return false;
        }
        if (!uri[1].equals("medicine")) {
            return false;
        }
        if (!fixed.contains(uri[2])) {
            return false;
        } else {
            if (Objects.equals(uri[2], "create")) {
                return uri.length == 3;
            } else {
                return uri.length == 4;
            }
        }
    }

    private <T> T getBodyToStringBuilder(HttpServletRequest request, Class<T> classToChange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                requestBody.append(line);
            }
        } catch (IOException e) {
            return null;
        }

//        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody.toString(), classToChange);
    }

    public void validateAuthority(Role role, int memberId, int elderlyId) {
        //Elderly elderly = elderlyRepository.findById(elderlyId).orElseThrow(() -> new CustomException(INVALID_AUTHORITY, "해당 elderlyId가 없습니다."));

        if(role.equals(Role.ELDERLY) && memberId != elderlyId) {
            throw new CustomException(INVALID_AUTHORITY,String.format("권한 없음. memberId : %d와 elderlyId: %d가 일치하지 않습니다.",memberId,elderlyId));
        }
        //body의 elderly id로 유저 찾아서 연결된 guardian id와 member id가 일치하는지 확인
        if (role.equals(Role.GURDIAN)) {
            Elderly managedElderly = elderlyRepository.findById(elderlyId);

            if (managedElderly == null || !managedElderly.getIsConnect()) {
                throw new CustomException(INVALID_AUTHORITY, String.format("member %d 와 연결된 guardian이 없습니다", elderlyId));
            } else if (managedElderly.getGuardianId() != memberId) {
                throw new CustomException(INVALID_AUTHORITY, String.format("권한 없음. memberId : %d와 연결된 guardianId: %d가 일치하지 않습니다.", elderlyId, memberId));
            }
        }

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        Role role = (Role) request.getAttribute("role");
        Member member = (Member) request.getAttribute("member");

        String requestUri = request.getRequestURI(); // 전체 URI 가져오기
        String httpMethod = request.getMethod(); // HTTP Method 가져오기

        if (!validateUri(requestUri) || role == null || member == null) {
            System.out.println(role);
            System.out.println(member);
            System.out.println(requestUri);
            throw new CustomException(MISMATCH_INFO, "role이나 memberId가 없거나 request uri가 옳바르지 않습니다.");
        }

        int memberId = member.getId();

        try{
            switch (httpMethod) {
                case "GET":
                    validateAuthority(role,memberId,Integer.parseInt(requestUri.split("/")[3]));
                    break;
                case "DELETE":
                case "PATCH":
                    Medicine medicine = medicineRepository.findById(Integer.parseInt(requestUri.split("/")[3]));
                    if (medicine == null) {
                        throw new CustomException(MISMATCH_INFO, "해당 medicineId가 없습니다.");
                    }
                    validateAuthority(role,memberId,medicine.getElderlyId());

                    break;
                case "POST":
                    RequestCreateMedicineDto body = getBodyToStringBuilder(request, RequestCreateMedicineDto.class);
                    if (body == null) {
                        throw new CustomException(MISMATCH_INFO, "body가 없습니다.");
                    }
                    validateAuthority(role,memberId,body.getElderlyId());

                    break;
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        return true;
    }


}


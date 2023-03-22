package seeNear.seeNear_BE.global.infra.sms;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import seeNear.seeNear_BE.global.infra.sms.dto.Message;
import seeNear.seeNear_BE.global.infra.sms.dto.SmsRequestDto;
import seeNear.seeNear_BE.global.infra.sms.dto.SmsResponseDto;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class NaverSmsService {
    @Value("${naver.sms.service_id}")
    private String serviceId;
    @Value("${naver.sms.access_key}")
    private String accessKey;
    @Value("${naver.sms.secret_key}")
    private String secretKey;
    @Value("${naver.sms.myNumber}")
    private String myNumber;


    public String sendSms(String phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String certificationNumber = RandomStringUtils.randomNumeric(4);
        String content = String.format("[SeeNear 본인확인]%n인증번호 [%s]를 입력해주세요.",certificationNumber);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(phoneNumber,content));

        SmsRequestDto requestBody = new SmsRequestDto("SMS",this.myNumber,content,messages);

        Long time = System.currentTimeMillis();

        WebClient client = WebClient.create();
        String signature = makeSignature(time);

        SmsResponseDto result = client.post()
                .uri(String.format("https://sens.apigw.ntruss.com/sms/v2/services/%s/messages",this.serviceId))
                .headers(headers -> {
                    headers.add("Content-Type","application/json");
                    headers.add("x-ncp-apigw-timestamp", time.toString());
                    headers.add("x-ncp-iam-access-key", this.accessKey);
                    headers.add("x-ncp-apigw-signature-v2", signature);
                })
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(SmsResponseDto.class)
                .block();

        if (!result.getStatusCode().equals("202")) {
            throw new RuntimeException("문자 전송 실패");
        }

        return certificationNumber;
    }

    public String makeSignature(Long time) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = String.format("/sms/v2/services/%s/messages",this.serviceId);
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

}

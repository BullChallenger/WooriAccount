package io.woori.account.wooriaccount.dto;

import io.woori.account.wooriaccount.common.dto.ResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Map;

class ResponseDTOTest {

    @Test
    void of() {


        ResponseDTO<Map<String, String>> mapResponseDTO = ResponseDTO.of(HttpStatus.OK, "ok", Map.of("d", "ddd", ":", "dd"));

        String message = mapResponseDTO.getMessage();
        Assertions.assertThat(mapResponseDTO.getHttpStatus()).isEqualTo(HttpStatus.OK);

        Map<String, String> data = mapResponseDTO.getData();
        for (Map.Entry<String, String> s : data.entrySet()) {

            System.out.println(s.getKey() +" : " + s.getValue());

        }


    }
}
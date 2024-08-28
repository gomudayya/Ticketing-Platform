package com.example.showservice.venue.dto;

import com.example.showservice.show.dto.SeatCountDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VenueCreateRequest {
    @NotBlank(message = "공연장 이름은 비워둘 수 없습니다.")
    private String venueName;
    @NotBlank(message = "공연장 주소는 비워둘 수 없습니다.")
    private String address;
    @NotBlank(message = "공연장 레이아웃 데이터는 비워둘 수 없습니다.")
    private String seatLayoutData;
    
    @NotEmpty(message = "좌석 섹션과 좌석 갯수정보를 입력해주세요")
    @Valid
    private List<SeatCountDto> seatCount; // <Section, 해당 Section 의 좌석갯수>
}

package com.example.showservice.show.dto.show;

import com.example.showservice.show.constant.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
public class ShowSearchCondition {
    private String genre;
    private String title;
    private String performer;
    private ShowStatus showStatus;
}

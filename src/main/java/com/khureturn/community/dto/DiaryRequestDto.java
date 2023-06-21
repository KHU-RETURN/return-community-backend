package com.khureturn.community.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.attribute.standard.Media;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class DiaryRequestDto {

    @Getter
    public static class CreateDiaryDto{

        private String media;
        private String title;
        private String content;
        private Boolean isAnonymous;
        private LocalDateTime eventDate;
        private Integer thumbnailIndex;

    }

    @Getter
    public static class UpdateDiaryDto{
        private String title;
        private String content;
        private Boolean isAnonymous;
    }




}

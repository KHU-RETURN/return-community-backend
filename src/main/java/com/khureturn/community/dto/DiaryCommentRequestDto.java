package com.khureturn.community.dto;

import lombok.Getter;

import java.util.List;

public class DiaryCommentRequestDto {

    @Getter
    public static class CreateCommentDto{
        private String content;
        //private List<String> hashtagList;
    }

    @Getter
    public static class UpdateCommentDto{
        private String content;
    }

}

package com.spoons.sehaehae.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AttachmentDTO {

    private Long no;
    private String name;
    private String route;
    private String saveName;
    private String extension;
    private Long size;
    private String ex;
    private Long reviewNo;
    private ReviewDTO review;
    private String thumbnail;

}

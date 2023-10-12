package com.spoons.sehaehae.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ProfileAttachmentDTO {
    private int profilePhotoNo;
    private int memberNo;
    private String originalProfilePhotoName;
    private String savedProfilePhotoName;
    private String savePathProfile;
    private String profileFileType;
}

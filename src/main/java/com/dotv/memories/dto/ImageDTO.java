package com.dotv.memories.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageDTO {
    private MultipartFile fileImage[];

    public MultipartFile[] getFileImage() {
        return fileImage;
    }

    public void setFileImage(MultipartFile[] fileImage) {
        this.fileImage = fileImage;
    }
}

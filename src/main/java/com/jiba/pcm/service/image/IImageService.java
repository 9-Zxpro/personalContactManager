package com.jiba.pcm.service.image;

import com.jiba.pcm.model.Image;
import org.springframework.web.multipart.MultipartFile;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    Image saveImages(MultipartFile file, String userId);
    void updateImage(MultipartFile file, Long imgId);
}

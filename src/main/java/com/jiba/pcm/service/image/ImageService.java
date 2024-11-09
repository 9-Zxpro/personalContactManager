package com.jiba.pcm.service.image;

import com.jiba.pcm.exceptions.ResourceNotFoundException;
import com.jiba.pcm.model.Image;
import com.jiba.pcm.model.User;
import com.jiba.pcm.repository.ImageRepository;
import com.jiba.pcm.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;

public class ImageService implements IImageService{
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private IUserService userService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("Image not found with id " + id);
        });
    }

    @Override
    public Image saveImages(MultipartFile file, String userId) {
        User user = userService.getUserById(userId);
        Image image = new Image();
        try {
            image.setName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            image.setUser(user);
            String buildpath = "pcm/images/download";
            image.setDownloadUrl(buildpath);
            Image savedImage = imageRepository.save(image);
            savedImage.setDownloadUrl(buildpath+savedImage.getId());
            image  = imageRepository.save(savedImage);

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    @Override
    public void updateImage(MultipartFile file, Long imgId) {
        Image image = getImageById(imgId);
        try {
            image.setName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

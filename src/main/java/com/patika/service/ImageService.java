package com.patika.service;

import com.patika.dto.response.ImageFileDTO;
import com.patika.entity.ImageFile;
import com.patika.exception.ResourceNotFoundException;
import com.patika.exception.message.ErrorMessage;
import com.patika.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ImageService {
    private final ImageRepository imageFileRepository;

    public ImageService(ImageRepository imageFileRepository) {
        this.imageFileRepository = imageFileRepository;
    }

    public String saveImage(MultipartFile file) {
        try {
            String fileName =//önce dosyanın ismine ulaşmalıyım ki kllanıcı aynı isimde kaydetmek isteyebilir
                    StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            //cleanPath--Parçalı gelen dosyadan istediğimz bilgileri almamızı sağlar

            String fileType = file.getContentType();
            long length = file.getSize();
            byte[] data = file.getBytes();

            ImageFile imageFile = new ImageFile(fileName, fileType, length, data);
            imageFileRepository.save(imageFile);

            return imageFile.getId();//Id döndürüyoruz
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ImageFile getImageById(String id) {
        ImageFile imageFile = imageFileRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND, id)));
        return imageFile;
    }

    public List<ImageFileDTO> getAllImages() {
        List<ImageFile> imageFiles = imageFileRepository.findAll();
        List<ImageFileDTO> imageFileDTOS =imageFiles.stream().map(imFile->{
            // URI olusturulmasini sagliyacagiz
            String imageUri = ServletUriComponentsBuilder.//URI üretmeeye yarar
                    fromCurrentContextPath(). // localhost:8080
                    path("/images/download/"). // localhost:8080/files/download
                    path(imFile.getId()).toUriString();// localhost:8080/files/download/fghvknsjfhdgkjn sdjh
            return new ImageFileDTO(imFile.getName(),
                    imageUri,
                    imFile.getType(),
                    imFile.getLength());
        }).collect(Collectors.toList());

        return imageFileDTOS;

    }
}

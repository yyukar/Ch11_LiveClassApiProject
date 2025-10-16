package com.patika.controller;

import com.patika.dto.response.ImageFileDTO;
import com.patika.dto.response.ImageSavedResponse;
import com.patika.entity.ImageFile;
import com.patika.exception.message.ResponseMessage;
import com.patika.service.ImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageFileService;

    public ImageController(ImageService imageFileService) {
        this.imageFileService = imageFileService;
    }


    @PostMapping("/upload")
    public ResponseEntity<ImageSavedResponse> uploadFile(
            @RequestParam("file") MultipartFile file){//görsel dosyanın parçalanmış halde gönderilmesini sağlar.(Parça derken ismi, bytları vs.)
        // Requestten gelecek görsel dosyayı MultipartFile türünde file dosyası ile maple
        // POSTMAN'de Body-key bölümüne file yazacağız
        String imageId = imageFileService.saveImage(file);
        ImageSavedResponse response =
                new ImageSavedResponse( ResponseMessage.IMAGE_SAVED_RESPONSE_MESSAGE,true,imageId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/display/{id}")
    public ResponseEntity<byte[]> displayFile(@PathVariable String id){
        ImageFile imageFile= imageFileService.getImageById(id);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(imageFile.getData(),
                header,
                HttpStatus.OK);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String id){//byte arraylar zaten fotoğraflarımdır. Bu yüzden dönen  byte[] olmalı
        ImageFile imageFile= imageFileService.getImageById(id);

        return ResponseEntity.ok().header(//body de ne göndereceksem onları header a koyarım
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=" + imageFile.getName()).//indirirken DB'ki isim otomatik gelir. Aksi durumda indirirken biz dosya ismini belirtmemizz gerekir
                body(imageFile.getData());
    }
    @GetMapping
    public ResponseEntity<List<ImageFileDTO>> getAllImages(){
        List<ImageFileDTO> allImageDTO = imageFileService.getAllImages();

        return ResponseEntity.ok(allImageDTO);
    }
}

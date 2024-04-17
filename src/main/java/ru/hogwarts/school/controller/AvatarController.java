package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/avatar")
public class AvatarController  {
    public final AvatarService avatarService;
    public final StudentService studentService;

    public AvatarController(AvatarService avatarService, StudentService studentService) {
        this.avatarService = avatarService;
        this.studentService = studentService;
    }
    @PostMapping(value = "/{id}/avatar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<String> uploadAvatar(@PathVariable Long id, MultipartFile avatar) throws IOException {
        if (avatar.getSize()>1024*300){
            return ResponseEntity.badRequest().body("Avatar's size is too large.");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();

    }
    @GetMapping("/{id}/avatar/preview")
    public ResponseEntity<byte[]> getAvatarPreview(@PathVariable Long id) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.ok().headers(headers).body(avatar.getData());
    }

    @GetMapping("/{id}/avatar")
    public void getAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();){
        response.setStatus(200);
        response.setContentType(avatar.getMediaType());
        response.setContentLength((int)avatar.getFileSize());
        is.transferTo(os);
         }
    }

}

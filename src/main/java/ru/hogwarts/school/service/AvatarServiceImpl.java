package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Value("${avatars.dir.path}")
    private String avatarDir;

    private final StudentService studentService;
    public AvatarRepository avatarRepository;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException {
        logger.info("Was invoked method - uploadAvatar");
        Student student = studentService.getStudent(studentId);
        logger.debug("In method uploadAvatar find student:{}, by studentId:{}", student, studentId);

        Path filePath = Path.of(avatarDir, studentId +"."+ getExtention(avatar.getOriginalFilename()));
        Files.createDirectories(filePath.getFileName());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatar.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
             ){
            bis.transferTo(bos);
        }
        Avatar newAvatar = findAvatar(studentId);
        newAvatar.setStudent(student);
        newAvatar.setFilePath(filePath.toString());
        newAvatar.setFileSize(avatar.getSize());
        newAvatar.setMediaType(avatar.getContentType());

        avatarRepository.save(newAvatar);

    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method - findAvatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    public String getExtention(String originalFilename) {
        logger.info("Was invoked method - getExtention");
        return originalFilename.substring(originalFilename.lastIndexOf(".")+1);
    }

    @Override
    public Collection<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method - getAllAvatars");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        logger.debug("In method getAllAvatars created pageRequest");
        return avatarRepository.findAll(pageRequest).getContent();
    }
}

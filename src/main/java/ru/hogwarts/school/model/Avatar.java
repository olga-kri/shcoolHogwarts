package ru.hogwarts.school.model;

import jakarta.persistence.*;

@Entity
public class Avatar {

    @Id
    @GeneratedValue
    private Long id;

    private String filePath;
    private long fileSize;
    private String mediaType;


    @Lob
    byte [] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    Student student;

    public Avatar() {}

    public String getFilePath() {
        return filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getMediaType() {
        return mediaType;
    }

    public byte[] getData() {
        return data;
    }

    public Student getStudent() {
        return student;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

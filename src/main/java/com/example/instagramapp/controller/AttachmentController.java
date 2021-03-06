package com.example.instagramapp.controller;

import com.example.instagramapp.entity.Attachment;
import com.example.instagramapp.entity.AttachmentContent;
import com.example.instagramapp.playload.ApiResponse;
import com.example.instagramapp.repository.AttachmentContentRepository;
import com.example.instagramapp.repository.AttachmentRepository;
import com.example.instagramapp.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    @Autowired
    AttachmentService attachmentService;

    @GetMapping("/info")
    public List<Attachment> getInfo(){
        List<Attachment> all = attachmentRepository.findAll();
        return all;
    }

    @PostMapping("/upload")
    public String uploadFile(MultipartHttpServletRequest request) throws IOException {

        Iterator<String> fileNames = request.getFileNames();

        MultipartFile file = request.getFile(fileNames.next());

        if (file != null) {
            //file haqida malumot olish uchun
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();
            Attachment attachment = new Attachment();

            attachment.setFileOriginalName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);

            Attachment save = attachmentRepository.save(attachment);

            //file ni byte [] saqlaymiz

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setAttachment(save);
            attachmentContent.setAsosiyContent(file.getBytes());

            AttachmentContent save1 = attachmentContentRepository.save(attachmentContent);
            return "Fayl saqlandi. ID si: " + save.getId();
        }
        return "Xatolik";
    }

    @GetMapping("/download/{id}")
    public void getFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);

        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);
            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + attachment.getFileOriginalName());
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getAsosiyContent(), response.getOutputStream());
            }
        }
    }

//    private static final String uploadDirectory = "filelar";
//
//    @PostMapping("/uploadSystem")
//    public String uploadFiletoFileSystem(MultipartHttpServletRequest request) throws IOException {
//        Iterator<String> fileNames = request.getFileNames();
//        MultipartFile file = request.getFile(fileNames.next());
//        if (file != null) {
//            String originalFilename = file.getOriginalFilename();
//
//            Attachment attachment = new Attachment();
//
//            attachment.setFileOriginalName(originalFilename);
//            attachment.setSize(file.getSize());
//            attachment.setContentType(file.getContentType());
//
//            String[] split = originalFilename.split("\\.");
//
//            String name = UUID.randomUUID().toString() + "." + split[split.length - 1];
//
//            attachment.setName(name);
//            attachmentRepository.save(attachment);
//
//            Path path = Paths.get(uploadDirectory +"/"+ name);
//            Files.copy(file.getInputStream(), path);
//            return "File saqlandi . ID si : " + attachment.getId();
//        }
//        return "Saqlanmadi";
//    }
//
//    @GetMapping("/downloadSystem/{id}")
//    public void getFileToFileSystem(@PathVariable Integer id,HttpServletResponse response) throws IOException {
//        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
//        if (optionalAttachment.isPresent()){
//            Attachment attachment = optionalAttachment.get();
//            response.setHeader("Content-Disposition",
//                    "attachment; filename=" + attachment.getFileOriginalName());
//            response.setContentType(attachment.getContentType());
//
//            FileInputStream fileInputStream=new FileInputStream(uploadDirectory+"/"+attachment.getName());
//
//            FileCopyUtils.copy(fileInputStream, response.getOutputStream());
//        }
//    }

    @PostMapping("/uploadFiles")
    public ApiResponse upload(MultipartHttpServletRequest request) {
        return attachmentService.upload(request);
    }
}

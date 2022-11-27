package com.ecom.service.impl;

import com.cloudinary.Cloudinary;
import com.ecom.service.CloudinaryService;
import com.ecom.util.HashingAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${spring.algorithm.sh.one}")
    private String algorithmName;
    private final HashingAlgorithm hashingAlgorithm;

    @Override
    public String uploadFile(MultipartFile fileImg, String filename) {
        try {
            File file = convertMultipartFileToFile(fileImg);
            String signature = generateSignature(filename);
            Map<String, Object> mapUpload = generateParameterToUpload(filename, signature);
            Map returnValue = cloudinary.uploader().upload(file, mapUpload);
            String url = (String) returnValue.get("url");
            return url;
        } catch (IOException e) {
            return "Error to read file, please retry!";
        }
    }

    private Map<String, Object> generateParameterToUpload(String name, String signature) {
        Map<String, Object> res = new HashMap<>();
        res.put("signature", signature);
        res.put("public_id", name);
        res.put("use_filename", false);
        res.put("overwrite", true);
        return res;
    }

    private File convertMultipartFileToFile(MultipartFile fileImg) throws IOException {
        File convertFile = new File(fileImg.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(fileImg.getBytes());
        fos.close();
        return convertFile;
    }

    public String generateSignature(String publicId) {
        String join = joinParameter(publicId) + apiKey;
        return hashingAlgorithm.hashingAlorithm(join, algorithmName);
    }

    private String joinParameter(String publicId) {
        return "public_id=" + publicId + "&timestamp=" + System.currentTimeMillis();
    }
}

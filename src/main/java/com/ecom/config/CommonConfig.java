package com.ecom.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ecom.util.HashingAlgorithm;
import com.ecom.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CommonConfig {
    @Value("${cloudinary.name}")
    private String cloudName;
    @Value("${cloudinary.api.key}")
    private String apiKey;
    @Value("${cloudinary.api.secret}")
    private String apiSecret;
    @Value("${cloudinary.secure}")
    private Boolean cloudSecure;
    @Value("${spring.algorithm.sh.one}")
    private String algorithmName;
    private final HashingAlgorithm hashingAlgorithm;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", cloudSecure
        ));
    }

    @Bean
    JwtUtil jwtUtil(@Value("${jwt.secret}") String key) {
        return new JwtUtil(hashingAlgorithm.hashingAlorithm(key, algorithmName));
    }

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

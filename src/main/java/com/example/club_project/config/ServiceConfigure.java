package com.example.club_project.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 프로젝트에서 사용하는 Service Bean 모아놓은 설정
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ServiceConfigure {

    private final AwsConfigure awsConfigure;

    @Bean
    public AmazonS3Client amazonS3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsConfigure.getAccessKey(), awsConfigure.getSecretKey());

        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(awsConfigure.getRegion())
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}

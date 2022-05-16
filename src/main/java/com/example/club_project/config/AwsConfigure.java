package com.example.club_project.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "cloud.aws.s3")
@PropertySource(value = {"classpath:/aws.properties"}, ignoreResourceNotFound = true)
public class AwsConfigure {

    private String region = "DUMMY_DATA_FOR_LOCAL_TEST";

    private String url = "DUMMY_DATA_FOR_LOCAL_TEST";

    private String bucket = "DUMMY_DATA_FOR_LOCAL_TEST";

    private String accessKey = "DUMMY_DATA_FOR_LOCAL_TEST";

    private String secretKey = "DUMMY_DATA_FOR_LOCAL_TEST";
}

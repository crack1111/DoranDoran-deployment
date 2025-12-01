package kr.ac.kopo.dorandoran;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class S3Config {
	//NrEPMIQLPIlIctNQan2-0xRXjLQtKK9EsZeDtDq4 토큰값
	@Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .endpointOverride(URI.create("https://2d61a3d30fdf403640dbe36a9a3ff76c.r2.cloudflarestorage.com"))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("9c5766c3b073b9989331db5666cee4dd", "e16c0efa1ad40a10802dbce14226bdba6744726436692a352ba606fc07737149")))
                .region(Region.of("auto"))
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true)
                        .build()
                )
                .build();
    }
}
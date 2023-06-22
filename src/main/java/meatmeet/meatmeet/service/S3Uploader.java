package meatmeet.meatmeet.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service
public class S3Uploader {
	private final AmazonS3Client amazonS3Client;
	
	public S3Uploader(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	public String upload(MultipartFile multipartFile, String dirName) throws IllegalArgumentException, FileNotFoundException, IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("[S4Uploader] MultipartFile -> File 전환 실패"));
		
		return upload(uploadFile, dirName);
	}
	
	private String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		
		removeNewFile(uploadFile);
		
		return uploadImageUrl;
	}
	
	private void removeNewFile(File targetFile) {
		if(targetFile.delete()) {
			log.info("[S3Uploader] 로컬 프로젝트에 저장된 파일 삭제");
		} else {
			log.info("[S3Uploader] 로컬 프로젝트에 저장된 파일 삭제 실패");
		}
	}
	
	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile)
					.withCannedAcl(CannedAccessControlList.PublicRead)
		);
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}
	
    private Optional<File> convert(MultipartFile file) throws  IOException {
    	String fileName = UUID.randomUUID() + file.getOriginalFilename();
        File convertFile = new File(fileName);
        
        log.info("[S3Uploader] fileName >> " + fileName);
        
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}

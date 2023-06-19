package meatmeet.meatmeet.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

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
	
	public Optional<File> convert(MultipartFile file) throws FileNotFoundException, IOException {
		File convertFile = new File(file.getOriginalFilename());
		
		if(convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			return Optional.of(convertFile);
		}
		return Optional.empty();
	}
	
	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}
	
	private void removeNewFile(File targetFile) {
		// convert() 실행되며 로컬 프로젝트에 파일 저장됨 -> removeNewFile() 실행해 삭제
		if(targetFile.delete()) {
			log.info("[MemberService - removeNewFile] 파일 삭제 완료");
		} else {
			log.info("[MemberService - removeNewFile] 파일 삭제 실패");
		}
	}
	
	public String upload(MultipartFile imgFile, String dirName) throws IOException {
		File uploadFile = convert(imgFile)
				.orElseThrow(() -> new IllegalArgumentException("[MemberService - upload] MultipartFile -> File 전환 실패"));
		return upload(uploadFile, dirName);
	}
	
	public String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		
		removeNewFile(uploadFile);
		
		return uploadImageUrl;
	}
}

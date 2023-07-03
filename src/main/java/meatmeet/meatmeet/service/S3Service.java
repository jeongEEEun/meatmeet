package meatmeet.meatmeet.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.opencsv.bean.CsvToBeanBuilder;

import io.netty.channel.unix.Buffer;
import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;

@Slf4j
@Component
@Service
public class S3Service {
	private final AmazonS3Client amazonS3Client;
	
	public S3Service(AmazonS3Client amazonS3Client) {
		this.amazonS3Client = amazonS3Client;
	}
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	private String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		
		removeNewFile(uploadFile);
		
		return uploadImageUrl;
	}
	
	public String upload(MultipartFile multipartFile, String dirName) throws IllegalArgumentException, FileNotFoundException, IOException {
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("[S4Uploader] MultipartFile -> File 전환 실패"));
		
		return upload(uploadFile, dirName);
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
    	String fileName = UUID.randomUUID().toString();
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
    
    public void deleteImgFile(String imgUrl) {
    	log.info(imgUrl);
    	
    	try {
    		amazonS3Client.deleteObject(bucket, imgUrl);
    	} catch (AmazonServiceException e) {
    		log.info(e.getErrorMessage());
		}
    }
    
	public String getCsv(String fileName) throws IOException {
		log.info("bucket >> " + bucket);
		log.info("fileName >> " + fileName);
		
		S3Object o = amazonS3Client.getObject(new GetObjectRequest(bucket, fileName));
		
		BufferedReader br = new BufferedReader(new InputStreamReader(o.getObjectContent()));
		String readCsv = "";
		
		String line;
		while ((line = br.readLine()) != null) {
			readCsv += line + "\n";
		}
		
		return readCsv;
	}
}

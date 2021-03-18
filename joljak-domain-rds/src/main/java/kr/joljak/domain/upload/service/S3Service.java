package kr.joljak.domain.upload.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import kr.joljak.core.exception.JoljakServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {
  private final AmazonS3Client amazonS3;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  public String uploadFile(MultipartFile file, String fileName, String path) {
    amazonS3.putObject(getPutObjectRequest(file, fileName, path));

    return getFileUrl(fileName, path);
  }

  private PutObjectRequest getPutObjectRequest(MultipartFile file, String fileName, String path) {
    PutObjectRequest putObjectRequest;

    try {
      putObjectRequest =  new PutObjectRequest(path, fileName, file.getInputStream(), getObjectMetadata(file));
    } catch (Exception e) {
      throw new JoljakServerException("[SERVER ERROR] - FAIL : file to PutObjectRequest");
    }

    return putObjectRequest;
  }

  private ObjectMetadata getObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(file.getContentType());

    return objectMetadata;
  }

  public String getFileUrl(String fileName, String path) {
    return String.valueOf(amazonS3.getUrl(getBucketName(path), getKey(path) + "/" + fileName));
  }

  public void deleteFile(String fileName, String path) {
    path = bucket + path;

    amazonS3.deleteObject(getBucketName(path), getKey(path) + "/" + fileName );
  }

  private String getBucketName(String path) {
    int index = path.indexOf("/");

    return path.substring(0, index);
  }

  private String getKey(String path) {
    int index = path.indexOf("/");

    return path.substring(index + 1);
  }

  public void deleteFile(String path) {
    amazonS3.deleteBucket(path);
  }

  public String getBucket() {
    return bucket;
  }
}

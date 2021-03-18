package kr.joljak.api.upload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class UploadTestRequest {
  private String classOf;
  private String message;
  private MultipartFile file;
}
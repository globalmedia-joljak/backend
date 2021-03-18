package kr.joljak.api.upload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadRequest {
  private String classOf;
  private String message;
}
package kr.joljak.api.upload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFileRequest {
  private String fileName;
  private String classOf;
}

package kr.joljak.api.upload.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFileRequest {
  @NotNull
  private String fileName;
  @NotNull
  private String classOf;
}

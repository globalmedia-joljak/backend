package kr.joljak.domain.upload.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MediaInfo {
  private String originalName;
  private String modifyName;
  private String fileExtension;
  private String fullPath;
  private String url;

  @Builder
  public MediaInfo(String originalName, String modifyName, String fileExtension,
      String bucketPath, String fullPath, String url) {
    this.originalName = originalName;
    this.modifyName = modifyName;
    this.fileExtension = fileExtension;
    this.fullPath = fullPath;
    this.url = url;
  }
}

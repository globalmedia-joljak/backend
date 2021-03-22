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
  private MediaType mediaType;

  @Builder
  public MediaInfo(String originalName, String modifyName, String fileExtension,
    String bucketPath, String fullPath, String url, MediaType mediaType) {
    this.originalName = originalName;
    this.modifyName = modifyName;
    this.fileExtension = fileExtension;
    this.fullPath = fullPath;
    this.url = url;
    this.mediaType = mediaType;
  }

  public static MediaInfo of(Media media) {
    return MediaInfo.builder()
      .originalName(media.getOriginalName())
      .modifyName(media.getModifyName())
      .fileExtension(media.getFileExtension())
      .fullPath(media.getFullPath())
      .url(media.getUrl())
      .mediaType(media.getMediaType())
      .build();
  }
}

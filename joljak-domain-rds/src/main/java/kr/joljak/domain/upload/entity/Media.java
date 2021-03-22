package kr.joljak.domain.upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "medias")
public class Media extends ExtendEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalName;

  private String modifyName;

  private String fileExtension;

  private String fullPath;

  @Column(nullable = false)
  private String url;

  private boolean uploadToS3;

  private MediaType mediaType;

  @Builder
  public Media(Long id, String originalName, String modifyName, String fileExtension,
      String fullPath, String url, boolean uploadToS3,
      MediaType mediaType) {
    this.id = id;
    this.originalName = originalName;
    this.modifyName = modifyName;
    this.fileExtension = fileExtension;
    this.fullPath = fullPath;
    this.url = url;
    this.uploadToS3 = uploadToS3;
    this.mediaType = mediaType;
  }
}

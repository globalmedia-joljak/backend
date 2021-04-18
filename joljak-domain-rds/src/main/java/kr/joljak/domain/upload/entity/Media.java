package kr.joljak.domain.upload.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.work.entity.Work;
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

  @Lob
  @Column(nullable = false)
  private String url;

  private boolean uploadToS3;

  private MediaType mediaType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "work_id", foreignKey = @ForeignKey(name = "FK_MEDIA_WORK"))
  private Work work;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", foreignKey = @ForeignKey(name = "FK_MEDIA_TEAM"))
  private Team team;

  @Builder
  public Media(Long id, String originalName, String modifyName, String fileExtension,
    String fullPath, String url, boolean uploadToS3,
    MediaType mediaType, Work work, Team team) {
    this.id = id;
    this.originalName = originalName;
    this.modifyName = modifyName;
    this.fileExtension = fileExtension;
    this.fullPath = fullPath;
    this.url = url;
    this.uploadToS3 = uploadToS3;
    this.mediaType = mediaType;
    this.work = work;
    this.team = team;
  }

}

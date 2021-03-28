package kr.joljak.domain.work.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.upload.entity.Media;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Works")
public class Work extends ExtendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String workName;

  @Column(nullable = false)
  private String teamName;

  @Column(nullable = false)
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> teamMember;

  @Column(nullable = false)
  private String content;

  private String teamVideoUrl;

  @OneToMany(mappedBy = "work",
    cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Media> images;

  @Builder
  public Work(String workName, String teamName,
    List<String> teamMember, String content, String teamVideoUrl,
    List<Media> images
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.images = images;
  }

  public static Work of(SimpleWork simpleWork, List<Media> imageList) {
    return Work.builder()
      .workName(simpleWork.getWorkName())
      .teamName(simpleWork.getTeamName())
      .teamMember(simpleWork.getTeamMember())
      .content(simpleWork.getContent())
      .teamVideoUrl(simpleWork.getTeamVideoUrl())
      .images(imageList)
      .build();
  }
}

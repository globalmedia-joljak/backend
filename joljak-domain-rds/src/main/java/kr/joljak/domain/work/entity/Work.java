package kr.joljak.domain.work.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.work.dto.SimpleWork;
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
  @Enumerated(EnumType.STRING)
  private ProjectCategory projectCategory;
  
  @Column(nullable = false)
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> teamMember;

  @Lob
  @Column(nullable = false)
  private String content;
  
  @Column(nullable = false)
  private String exhibitedYear;
  
  @Column
  private String teamVideoUrl;
  
  @OneToMany(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "work_id")
  @Size(max = 5)
  private List<Media> images;
  
  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "media_id")
  private Media media;
  
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;
  
  @Builder
  public Work(String workName, String teamName,
    List<String> teamMember, String content, String teamVideoUrl,
    List<Media> images, User user, ProjectCategory projectCategory, String exhibitedYear,
    Media media
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.projectCategory = projectCategory;
    this.exhibitedYear = exhibitedYear;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.images = images;
    this.user = user;
    this.media = media;
  }
  
  public void setWorkName(String workName) {
    this.workName = workName;
  }
  
  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }
  
  public void setTeamMember(List<String> teamMember) {
    this.teamMember = teamMember;
  }
  
  public void setContent(String content) {
    this.content = content;
  }
  
  public void setTeamVideoUrl(String teamVideoUrl) {
    this.teamVideoUrl = teamVideoUrl;
  }
  
  public void setImages(List<Media> images) {
    this.images = images;
  }
  
  public void setProjectCategory(ProjectCategory projectCategory) { this.projectCategory = projectCategory; }
  
  public void setExhibitedYear(String exhibitedYear) { this.exhibitedYear = exhibitedYear;  }
  
  public void setUser(User user) {
    this.user = user;
  }
  
  public void setMedia(Media media){
    this.media = media;
  }
  
  public static Work of(SimpleWork simpleWork, List<Media> imageList, Media file) {
    return Work.builder()
      .workName(simpleWork.getWorkName())
      .teamName(simpleWork.getTeamName())
      .teamMember(simpleWork.getTeamMember())
      .content(simpleWork.getContent())
      .teamVideoUrl(simpleWork.getTeamVideoUrl())
      .projectCategory(simpleWork.getProjectCategory())
      .exhibitedYear(simpleWork.getExhibitedYear())
      .images(imageList)
      .user(simpleWork.getUser())
      .media(file)
      .build();
  }
}

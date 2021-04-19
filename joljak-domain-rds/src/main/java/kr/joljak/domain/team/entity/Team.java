package kr.joljak.domain.team.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Teams")
public class Team extends ExtendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String teamName;

  @Column(nullable = false)
  private ProjectCategory projectCategory;

  @Lob
  @Column(nullable = false)
  private String content;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  private String mediaArtMember;

  @Column
  private String developerMember;

  @Column
  private String designerMember;

  @Column
  private String plannerMember;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "media_id")
  private Media media;

  @Builder
  public Team(
    String teamName, ProjectCategory projectCategory, String content, User user,
    String mediaArtMember, String designerMember,
    String developerMember, String plannerMember, Media media
  ) {
    this.teamName = teamName;
    this.projectCategory = projectCategory;
    this.content = content;
    this.user = user;
    this.mediaArtMember = mediaArtMember;
    this.designerMember = designerMember;
    this.developerMember = developerMember;
    this.plannerMember = plannerMember;
    this.media = media;
  }

  public void setTeamName(String teamName){
    this.teamName = teamName;
  }
  public void setProjectCategory(ProjectCategory projectCategory){
    this.projectCategory = projectCategory;
  }
  public void setMediaArtMember(String mediaArtMember){
    this.mediaArtMember = mediaArtMember;
  }
  public void setDesignerMember(String designerMember){
    this.designerMember = designerMember;
  }
  public void setDeveloperMember(String developerMember){
    this.developerMember = developerMember;
  }
  public void setPlannerMember(String plannerMember){
    this.plannerMember = plannerMember;
  }
  public void setMedia(Media media){
    this.media = media;
  }
  
  public static Team of(SimpleTeam simpleTeam, Media media) {
    return Team.builder()
      .teamName(simpleTeam.getTeamName())
      .projectCategory(simpleTeam.getProjectCategory())
      .content(simpleTeam.getContent())
      .user(simpleTeam.getAuthor())
      .mediaArtMember(simpleTeam.getMediaArtMember())
      .designerMember(simpleTeam.getDesignerMember())
      .developerMember(simpleTeam.getDeveloperMember())
      .plannerMember(simpleTeam.getPlannerMember())
      .media(media)
      .build();
  }

}

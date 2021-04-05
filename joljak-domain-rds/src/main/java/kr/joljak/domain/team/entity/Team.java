package kr.joljak.domain.team.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.user.entity.User;
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
  private String category;

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

  @OneToMany(fetch = FetchType.EAGER,
    cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "team_id")
  private List<Media> images;

  @Builder
  public Team(
    String teamName, String category, String content, User user,
    String mediaArtMember, String designerMember,
    String developerMember, String plannerMember, List<Media> images
  ) {
    this.teamName = teamName;
    this.category = category;
    this.content = content;
    this.user = user;
    this.mediaArtMember = mediaArtMember;
    this.designerMember = designerMember;
    this.developerMember = developerMember;
    this.plannerMember = plannerMember;
    this.images = images;
  }

  public static Team of(SimpleTeam simpleTeam, List<Media> imageList) {
    return Team.builder()
      .teamName(simpleTeam.getTeamName())
      .category(simpleTeam.getCategory())
      .content(simpleTeam.getContent())
      .user(simpleTeam.getAuthor())
      .mediaArtMember(simpleTeam.getMediaArtMember())
      .designerMember(simpleTeam.getDesignerMember())
      .developerMember(simpleTeam.getDeveloperMember())
      .plannerMember(simpleTeam.getPlannerMember())
      .images(imageList)
      .build();
  }

}

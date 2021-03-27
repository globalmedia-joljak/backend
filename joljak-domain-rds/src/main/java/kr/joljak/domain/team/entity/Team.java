package kr.joljak.domain.team.entity;

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
import kr.joljak.domain.team.dto.SimpleTeam;
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
@Table(name = "Teams")
public class Team extends ExtendEntity {

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

  @OneToMany(mappedBy = "team",
    cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Media> images;

  @Builder
  public Team(String workName, String teamName,
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

  public static Team of(SimpleTeam simpleTeam, List<Media> imageList) {
    return Team.builder()
      .workName(simpleTeam.getWorkName())
      .teamName(simpleTeam.getTeamName())
      .teamMember(simpleTeam.getTeamMember())
      .content(simpleTeam.getContent())
      .teamVideoUrl(simpleTeam.getTeamVideoUrl())
      .images(imageList)
      .build();
  }
}

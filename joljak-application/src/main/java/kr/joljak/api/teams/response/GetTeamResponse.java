package kr.joljak.api.teams.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetTeamResponse {

  private Long id;
  private String teamName;
  private String content;
  private ProjectCategory category;
  private String mediaArtMember;
  private String developerMember;
  private String designerMember;
  private String plannerMember;
  private String author;
  private List<MediaInfo> imageInfoList;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  @Builder
  public GetTeamResponse(
    Long id, String teamName, String content,
    ProjectCategory category, String mediaArtMember, String designerMember,
    String developerMember, String plannerMember,
    String author, List<MediaInfo> imageInfoList, LocalDateTime createdDate,
    LocalDateTime modifiedDate
  ) {
    this.id = id;
    this.teamName = teamName;
    this.content = content;
    this.category = category;
    this.mediaArtMember = mediaArtMember;
    this.designerMember = designerMember;
    this.developerMember = developerMember;
    this.plannerMember = plannerMember;
    this.author = author;
    this.imageInfoList = imageInfoList;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  public static GetTeamResponse of(Team team) {
    List<MediaInfo> imageList = null;

    if (team.getImages() != null) {
      imageList = team.getImages().stream()
        .map(image -> MediaInfo.of(image))
        .collect(Collectors.toList());
    }

    return GetTeamResponse.builder()
      .id(team.getId())
      .teamName(team.getTeamName())
      .content(team.getContent())
      .category(team.getCategory())
      .mediaArtMember(team.getMediaArtMember())
      .designerMember(team.getDesignerMember())
      .developerMember(team.getDeveloperMember())
      .plannerMember(team.getPlannerMember())
      .author(team.getUser().getName())
      .imageInfoList(imageList)
      .createdDate(team.getCreatedDate())
      .modifiedDate(team.getModifiedDate())
      .build();
  }

}

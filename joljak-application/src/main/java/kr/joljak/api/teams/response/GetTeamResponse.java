package kr.joljak.api.teams.response;

import java.time.LocalDateTime;
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
  private MediaInfo fileInfo;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  @Builder
  public GetTeamResponse(
    Long id, String teamName, String content,
    ProjectCategory category, String mediaArtMember, String designerMember,
    String developerMember, String plannerMember,
    String author, MediaInfo fileInfo, LocalDateTime createdDate,
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
    this.fileInfo = fileInfo;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  public static GetTeamResponse of(Team team) {
    MediaInfo fileInfo = null;

    if (team.getMedia() != null) {
      fileInfo = MediaInfo.of(team.getMedia());
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
      .fileInfo(fileInfo)
      .createdDate(team.getCreatedDate())
      .modifiedDate(team.getModifiedDate())
      .build();
  }

}

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
public class RegisterTeamResponse {

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

  @Builder
  public RegisterTeamResponse(
    Long id, String teamName, String content,
    ProjectCategory category, String mediaArtMember, String designerMember,
    String developerMember, String plannerMember,
    String author, LocalDateTime createdDate, List<MediaInfo> imageInfoList
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
  }

}

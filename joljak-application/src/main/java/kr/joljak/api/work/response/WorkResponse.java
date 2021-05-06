package kr.joljak.api.work.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkResponse {

  @NotNull
  private Long id;
  @NotNull
  private String workName;
  @NotNull
  private ProjectCategory projectCategory;
  @NotNull
  private String teamName;
  @NotNull
  private List<String> teamMember;
  @NotNull
  private String exhibitedYear;
  @NotNull
  private String content;
  private String teamVideoUrl;
  private List<MediaInfo> imageInfoList;
  private MediaInfo fileInfo;
  private LocalDateTime createDate;
  private LocalDateTime modifiedDate;
  private String author;

  @Builder
  public WorkResponse(
    Long id, String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl, List<MediaInfo> imageInfoList,
    LocalDateTime createDate, LocalDateTime modifiedDate, ProjectCategory projectCategory,
    String exhibitedYear, MediaInfo fileInfo, String author
  ) {
    this.id = id;
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.exhibitedYear = exhibitedYear;
    this.content = content;
    this.projectCategory = projectCategory;
    this.teamVideoUrl = teamVideoUrl;
    this.imageInfoList = imageInfoList;
    this.createDate = createDate;
    this.modifiedDate = modifiedDate;
    this.fileInfo = fileInfo;
    this.author = author;
  }

  public static WorkResponse of(Work work) {
    List<MediaInfo> imageList = null;
    MediaInfo file = null;

    if (work.getImages() != null && work.getImages().size()!=0) {
      imageList = work.getImages().stream()
        .map(image -> MediaInfo.of(image))
        .collect(Collectors.toList());
    }
    
    if(work.getMedia() != null){
      file = MediaInfo.of(work.getMedia());
    }

    return WorkResponse.builder()
      .id(work.getId())
      .workName(work.getWorkName())
      .teamName(work.getTeamName())
      .teamMember(work.getTeamMember())
      .content(work.getContent())
      .exhibitedYear(work.getExhibitedYear())
      .projectCategory(work.getProjectCategory())
      .teamVideoUrl(work.getTeamVideoUrl())
      .imageInfoList(imageList)
      .createDate(work.getCreatedDate())
      .modifiedDate(work.getModifiedDate())
      .fileInfo(file)
      .author(work.getUser().getName())
      .build();
  }
}

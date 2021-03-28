package kr.joljak.api.work.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkResponse {

  @NotNull
  private String workName;
  @NotNull
  private String teamName;
  @NotNull
  private List<String> teamMember;
  @NotNull
  private String content;
  private String teamVideoUrl;
  private List<MediaInfo> imageInfoList;
  private LocalDateTime createDate;
  private LocalDateTime modifiedDate;

  @Builder
  public WorkResponse(
    String workName, String teamName, List<String> teamMember,
    String content, String teamVideoUrl, List<MediaInfo> imageInfoList,
    LocalDateTime createDate, LocalDateTime modifiedDate
  ){
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.imageInfoList = imageInfoList;
    this.createDate = createDate;
    this.modifiedDate = modifiedDate;
  }

  public static WorkResponse of(Work work){
    List<MediaInfo> imageList = null;

    if(work.getImages() != null){
      imageList = work.getImages().stream()
        .map(image -> MediaInfo.of(image))
        .collect(Collectors.toList());
    }

    return WorkResponse.builder()
      .workName(work.getWorkName())
      .teamName(work.getTeamName())
      .teamMember(work.getTeamMember())
      .content(work.getContent())
      .teamVideoUrl(work.getTeamVideoUrl())
      .imageInfoList(imageList)
      .createDate(work.getCreatedDate())
      .modifiedDate(work.getModifiedDate())
      .build();
  }
}

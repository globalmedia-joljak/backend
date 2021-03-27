package kr.joljak.api.team.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeamResponse {

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
  public TeamResponse(
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

  public static TeamResponse of(Team team){
    List<MediaInfo> imageList = null;

    System.out.println();
    System.out.println("!@#!@#");
    for(Media a : team.getImages()){
      System.out.println(a);
    }

    System.out.println();

    if(team.getImages() != null){
      imageList = team.getImages().stream()
        .map(image -> MediaInfo.of(image))
        .collect(Collectors.toList());
    }

    return TeamResponse.builder()
      .workName(team.getWorkName())
      .teamName(team.getTeamName())
      .teamMember(team.getTeamMember())
      .content(team.getContent())
      .teamVideoUrl(team.getTeamVideoUrl())
      .imageInfoList(imageList)
      .createDate(team.getCreatedDate())
      .modifiedDate(team.getModifiedDate())
      .build();
  }
}

package kr.joljak.api.work.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RegisterWorkRequest {
  
  @NotNull
  private String workName;
  @NotNull
  private String teamName;
  @NotNull
  private ProjectCategory projectCategory;
  @NotNull
  private String exhibitedYear;
  @NotNull
  private List<String> teamMember;
  @NotNull
  private String content;
  private String teamVideoUrl;
  private List<MultipartFile> images;
  @Setter
  private MultipartFile file;
  
  @Builder
  public RegisterWorkRequest(String workName, String teamName, ProjectCategory projectCategory,
    List<String> teamMember, String content, String teamVideoUrl, String exhibitedYear,
    List<MultipartFile> images, MultipartFile file
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.projectCategory = projectCategory;
    this.teamMember = teamMember;
    this.content = content;
    this.exhibitedYear = exhibitedYear;
    this.teamVideoUrl = teamVideoUrl;
    this.images = images;
    this.file = file;
  }
  
  public static SimpleWork toDomainWorkRequest(RegisterWorkRequest registerWorkRequest) {
    return SimpleWork.builder()
      .workName(registerWorkRequest.getWorkName())
      .teamName(registerWorkRequest.getTeamName())
      .teamMember(registerWorkRequest.getTeamMember())
      .projectCategory(registerWorkRequest.getProjectCategory())
      .exhibitedYear(registerWorkRequest.getExhibitedYear())
      .content(registerWorkRequest.getContent())
      .teamVideoUrl(registerWorkRequest.getTeamVideoUrl())
      .images(registerWorkRequest.getImages())
      .file(registerWorkRequest.getFile())
      .build();
  }
  
}

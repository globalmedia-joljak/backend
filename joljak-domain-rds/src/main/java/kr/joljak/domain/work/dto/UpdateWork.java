package kr.joljak.domain.work.dto;

import java.util.List;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateWork {

  private String workName;
  private String teamName;
  private List<String> teamMember;
  private String content;
  private ProjectCategory projectCategory;
  private String exhibitedYear;
  private String teamVideoUrl;
  private User user;
  private List<MultipartFile> images;
  private List<String> deleteFileName;

  @Builder
  public UpdateWork(
    String workName, String teamName, List<String> teamMember, ProjectCategory projectCategory,
    String content, String teamVideoUrl, User user, List<String> deleteFileName,
    String exhibitedYear, List<MultipartFile> images
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.teamMember = teamMember;
    this.content = content;
    this.exhibitedYear = exhibitedYear;
    this.images = images;
    this.projectCategory = projectCategory;
    this.teamVideoUrl = teamVideoUrl;
    this.user = user;
    this.deleteFileName = deleteFileName;
  }
}

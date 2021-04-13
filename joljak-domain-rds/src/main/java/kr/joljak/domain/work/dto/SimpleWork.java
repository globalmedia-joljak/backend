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
public class SimpleWork {
  
  private String workName;
  private String teamName;
  private List<String> teamMember;
  private ProjectCategory projectCategory;
  private String exhibitedYear;
  private String content;
  private String teamVideoUrl;
  private User user;
  private List<MultipartFile> images;
  
  @Builder
  public SimpleWork(
    String workName, String teamName, List<String> teamMember, String exhibitedYear,
    String content, String teamVideoUrl, User user, ProjectCategory projectCategory,
    List<MultipartFile> images
  ) {
    this.workName = workName;
    this.teamName = teamName;
    this.projectCategory = projectCategory;
    this.exhibitedYear = exhibitedYear;
    this.teamMember = teamMember;
    this.content = content;
    this.teamVideoUrl = teamVideoUrl;
    this.user = user;
    this.images = images;
    
  }
  
  public void setUser(User user) {
    this.user = user;
  }
}

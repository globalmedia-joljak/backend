package kr.joljak.domain.Ideaboard.dto;

import java.util.List;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleIdeaBoard {

  private String title;
  private String content;
  private ProjectStatus status;
  private String contact;
  private List<UserProjectRole> requiredPositions;
  private User user;
  private UserProjectRole mainRole;
  private MultipartFile file;
  private String deleteFileName;
  private ProjectCategory category;

  @Builder
  public SimpleIdeaBoard(String title, String content, String contact,
      ProjectStatus status, User user,
    List<UserProjectRole> requiredPositions, MultipartFile file,
    UserProjectRole mainRole, String deleteFileName, ProjectCategory category
  ) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.status = status;
    this.contact = contact;
    this.requiredPositions = requiredPositions;
    this.mainRole = mainRole;
    this.file = file;
    this.deleteFileName = deleteFileName;
    this.category = category;
  }

}

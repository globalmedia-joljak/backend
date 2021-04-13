package kr.joljak.api.ideaboard.request;

import java.util.List;
import javax.validation.constraints.NotNull;


import kr.joljak.domain.Ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardRequest {

  @NotNull
  private String title;
  @NotNull
  private String content;
  @NotNull
  private ProjectStatus status;
  private String contact;
  private List<UserProjectRole> requiredPositions;
  private UserProjectRole mainRole;
  @Setter
  private MultipartFile file;
  private String deleteFileName;

  @Builder
  public IdeaBoardRequest(String title, String content, String contact,
    ProjectStatus status, String deleteFileName,
    List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole, MultipartFile file) {
    this.title = title;
    this.content = content;
    this.contact = contact;
    this.status = status;
    this.requiredPositions = requiredPositions;
    this.deleteFileName = deleteFileName;
    this.mainRole = mainRole;
    this.file = file;
  }

  public static SimpleIdeaBoard toDomainIdeaBoardRequest(IdeaBoardRequest ideaBoardRequest) {
    return SimpleIdeaBoard.builder()
      .title(ideaBoardRequest.getTitle())
      .content(ideaBoardRequest.getContent())
      .status(ideaBoardRequest.getStatus())
      .file(ideaBoardRequest.getFile())
      .contact(ideaBoardRequest.getContact())
      .requiredPositions(ideaBoardRequest.getRequiredPositions())
      .mainRole(ideaBoardRequest.getMainRole())
      .deleteFileName(ideaBoardRequest.getDeleteFileName())
      .build();
  }

}

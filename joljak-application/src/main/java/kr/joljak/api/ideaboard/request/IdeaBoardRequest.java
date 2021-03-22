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

  @Builder
  public IdeaBoardRequest(String title, String content, String contact,
    ProjectStatus status,
    List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole, MultipartFile file) {
    this.title = title;
    this.content = content;

    this.contact = contact;
    this.status = status;
    this.requiredPositions = requiredPositions;

    this.mainRole = mainRole;
  }

  public static SimpleIdeaBoard toDomainIdeaBoardRequest(IdeaBoardRequest ideaBoardRequest) {
    return SimpleIdeaBoard.builder()
      .title(ideaBoardRequest.getTitle())
      .content(ideaBoardRequest.getContent())
      .status(ideaBoardRequest.getStatus())
      .contact(ideaBoardRequest.getContact())
      .requiredPositions(ideaBoardRequest.getRequiredPositions())
      .mainRole(ideaBoardRequest.getMainRole())
      .build();
  }

}

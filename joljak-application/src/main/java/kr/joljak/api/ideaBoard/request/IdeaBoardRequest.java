package kr.joljak.api.ideaBoard.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
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
  private String classOf;
  @NotNull
  private IdeaBoard.Status status;
  private String contact;
  // User
  private List<UserProjectRole> requiredPosition;
  private String name;
  private UserProjectRole mainRole;

  @Builder
  public IdeaBoardRequest(String title, String content, String contact, String classOf,
    Status status,
    List<UserProjectRole> requiredPosition, String name,
    UserProjectRole mainRole, MultipartFile file) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.contact = contact;
    this.status = status;
    this.requiredPosition = requiredPosition;
    this.name = name;
    this.mainRole = mainRole;
  }

  public static SimpleIdeaBoard toDomainIdeaBoardRequest(IdeaBoardRequest ideaBoardRequest) {
    return SimpleIdeaBoard.builder()
      .title(ideaBoardRequest.getTitle())
      .content(ideaBoardRequest.getContent())
      .classOf(ideaBoardRequest.getClassOf())
      .status(ideaBoardRequest.getStatus())
      .contact(ideaBoardRequest.getContact())
      .requiredPosition(ideaBoardRequest.getRequiredPosition())
      .name(ideaBoardRequest.getName())
      .mainRole(ideaBoardRequest.getMainRole())
      .build();
  }

}

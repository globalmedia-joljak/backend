package kr.joljak.api.ideaBoard.response;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardResponse {

  @NotNull
  private IdeaBoard.Status status;
  @NotNull
  private String title;
  @NotNull
  private String content;
  private String contact;
  @NotNull
  private String classOf;
  // User
  private List<UserProjectRole> requiredPosition;
  private String name;
  private UserProjectRole mainProjectRole;
  // File, Image Upload
  private Media file;

  @Builder
  public IdeaBoardResponse(String title, String content, String contact, String classOf,
    Status status,
    List<UserProjectRole> requiredPosition, String name,
    UserProjectRole mainProjectRole, Media file) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.contact = contact;
    this.requiredPosition = requiredPosition;
    this.name = name;
    this.mainProjectRole = mainProjectRole;
    this.file = file;
  }

}

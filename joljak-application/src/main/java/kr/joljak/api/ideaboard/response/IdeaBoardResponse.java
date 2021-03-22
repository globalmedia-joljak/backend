package kr.joljak.api.ideaboard.response;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.ProjectStatus;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardResponse {

  @NotNull
  private ProjectStatus status;
  @NotNull
  private String title;
  @NotNull
  private String content;
  private String contact;
  @NotNull
  private String classOf;
  private List<UserProjectRole> requiredPositions;
  private String name;
  private UserProjectRole mainProjectRole;
  private MediaInfo fileInfo;

  @Builder
  public IdeaBoardResponse(String title, String content, String contact, String classOf,
    ProjectStatus status,
    List<UserProjectRole> requiredPositions, String name,
    UserProjectRole mainProjectRole, MediaInfo fileInfo) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.contact = contact;
    this.requiredPositions = requiredPositions;
    this.name = name;
    this.mainProjectRole = mainProjectRole;
    this.fileInfo = fileInfo;
  }

  public static IdeaBoardResponse of(IdeaBoard ideaBoard) {
    return IdeaBoardResponse.builder()
      .title(ideaBoard.getTitle())
      .content(ideaBoard.getContent())
      .status(ideaBoard.getStatus())
      .contact(ideaBoard.getContact())
      .classOf(ideaBoard.getUser().getClassOf())
      .requiredPositions(ideaBoard.getRequiredPosiotions())
      .name(ideaBoard.getUser().getName())
      .mainProjectRole(ideaBoard.getUser().getMainProjectRole())
      .fileInfo(MediaInfo.of(ideaBoard.getFile()))
      .build();
  }

}

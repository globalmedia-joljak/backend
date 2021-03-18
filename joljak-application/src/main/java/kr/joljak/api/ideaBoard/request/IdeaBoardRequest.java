package kr.joljak.api.ideaBoard.request;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoardRequest;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
  @NotNull
  private List<UserProjectRole> requiredPosition;

  @Builder
  public IdeaBoardRequest(String title, String content, String classOf, Status status,
    List<UserProjectRole> requiredPosition) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.requiredPosition = requiredPosition;
  }

  public static SimpleIdeaBoardRequest toDomainIdeaBoardRequest(IdeaBoardRequest ideaBoardRequest){
    return SimpleIdeaBoardRequest.builder()
      .title(ideaBoardRequest.getTitle())
      .content(ideaBoardRequest.getContent())
      .classOf(ideaBoardRequest.getClassOf())
      .status(ideaBoardRequest.getStatus())
      .requiredPosition(ideaBoardRequest.getRequiredPosition())
      .build();
  }

}

package kr.joljak.domain.IdeaBoard.dto;

import java.util.List;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleIdeaBoardRequest {

  private String title;
  private String content;
  private String classOf;
  private Status status;
  private List<UserProjectRole> requiredPosition;

  @Builder
  public SimpleIdeaBoardRequest(String title, String content, String classOf, Status status, List<UserProjectRole> requiredPosition){
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.requiredPosition = requiredPosition;
  }

}

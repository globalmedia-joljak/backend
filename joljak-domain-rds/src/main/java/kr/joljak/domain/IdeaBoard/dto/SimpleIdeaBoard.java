package kr.joljak.domain.IdeaBoard.dto;

import java.util.List;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleIdeaBoard {

  private String title;
  private String content;
  private String classOf;
  private Status status;
  private String contact;
  private List<UserProjectRole> requiredPosition;
  private String name;
  private UserProjectRole mainRole;

  @Builder
  public SimpleIdeaBoard(String title, String content, String contact, String classOf,
    Status status,
    List<UserProjectRole> requiredPosition, String name,
    UserProjectRole mainRole) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.contact = contact;
    this.status = status;
    this.requiredPosition = requiredPosition;
    this.name = name;
    this.mainRole = mainRole;
  }

}

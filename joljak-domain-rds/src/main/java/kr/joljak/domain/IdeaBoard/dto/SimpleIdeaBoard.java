package kr.joljak.domain.IdeaBoard.dto;

import java.util.List;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.ProjectStatus;
import kr.joljak.domain.user.entity.User;
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

  private ProjectStatus status;
  private String contact;
  private List<UserProjectRole> requiredPositions;
  private User user;
  private UserProjectRole mainRole;

  @Builder
  public SimpleIdeaBoard(String title, String content, String contact,
      ProjectStatus status, User user,
    List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.status = status;
    this.contact = contact;
    this.requiredPositions = requiredPositions;
    this.mainRole = mainRole;
  }

  public static SimpleIdeaBoard of(IdeaBoard ideaBoard){
    return SimpleIdeaBoard.builder()
      .title(ideaBoard.getTitle())
      .content(ideaBoard.getContent())
      .status(ideaBoard.getStatus())
      .contact(ideaBoard.getContact())
      .user(ideaBoard.getUser())
      .requiredPositions(ideaBoard.getRequiredPosiotions())
      .mainRole(ideaBoard.getUser().getMainProjectRole())
      .build();
  }

}

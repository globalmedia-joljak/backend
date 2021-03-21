package kr.joljak.domain.IdeaBoard.dto;

import java.util.List;
import kr.joljak.domain.IdeaBoard.entity.ProjectStatus;
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

  private UserProjectRole mainRole;

  @Builder
  public SimpleIdeaBoard(String title, String content, String contact,
      ProjectStatus status,
    List<UserProjectRole> requiredPositions,
    UserProjectRole mainRole) {
    this.title = title;
    this.content = content;

    this.contact = contact;
    this.status = status;
    this.requiredPositions = requiredPositions;

    this.mainRole = mainRole;
  }

}

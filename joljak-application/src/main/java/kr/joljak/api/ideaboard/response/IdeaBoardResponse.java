package kr.joljak.api.ideaboard.response;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.entity.ProjectStatus;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.work.entity.ProjectCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardResponse {

  @NotNull
  private Long id;
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
  private MediaInfo mediaInfo;
  private LocalDateTime createDate;
  private LocalDateTime modifiedDate;
  private ProjectCategory category;

  @Builder
  public IdeaBoardResponse(Long id, String title, String content, String contact, String classOf,
    ProjectStatus status,
    List<UserProjectRole> requiredPositions, String name,
    UserProjectRole mainProjectRole, MediaInfo mediaInfo,
    LocalDateTime createDate, LocalDateTime modifiedDate, ProjectCategory category
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.contact = contact;
    this.requiredPositions = requiredPositions;
    this.name = name;
    this.mainProjectRole = mainProjectRole;
    this.mediaInfo = mediaInfo;
    this.createDate = createDate;
    this.modifiedDate = modifiedDate;
    this.category = category;
  }

  public static IdeaBoardResponse of(IdeaBoard ideaBoard) {
    MediaInfo mediaInfo = null;

    if (ideaBoard.getMedia() != null) {
      mediaInfo = MediaInfo.of(ideaBoard.getMedia());
    }

    return IdeaBoardResponse.builder()
      .id(ideaBoard.getId())
      .title(ideaBoard.getTitle())
      .content(ideaBoard.getContent())
      .status(ideaBoard.getStatus())
      .contact(ideaBoard.getContact())
      .classOf(ideaBoard.getUser().getClassOf())
      .requiredPositions(ideaBoard.getRequiredPosiotions())
      .name(ideaBoard.getUser().getName())
      .mainProjectRole(ideaBoard.getUser().getMainProjectRole())
      .mediaInfo(mediaInfo)
      .createDate(ideaBoard.getCreatedDate())
      .modifiedDate(ideaBoard.getModifiedDate())
      .category(ideaBoard.getCategory())
      .build();
  }

}

package kr.joljak.api.ideaBoard.response;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IdeaBoardResponse {

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
  private UserProjectRole mainProjectRole;
  // File, Image Upload
  private MediaInfo file;
  private MediaInfo image;

  @Builder
  public IdeaBoardResponse(String title, String content, String contact, String classOf,
    Status status,
    List<UserProjectRole> requiredPosition, String name,
    UserProjectRole mainProjectRole, MediaInfo file, MediaInfo image) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.status = status;
    this.contact = contact;
    this.requiredPosition = requiredPosition;
    this.name = name;
    this.mainProjectRole = mainProjectRole;
    this.file = file;
    this.image = image;
  }

}

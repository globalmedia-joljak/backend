package kr.joljak.domain.IdeaBoard.dto;

import java.util.List;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard.Status;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleIdeaBoard {

  private String title;
  private String content;
  private String classOf;
  private String contact;
  private Status status;
  private List<UserProjectRole> requiredPosition;
  private String phoneNumber;
  private String name;
  private UserProjectRole mainRole;
  private MultipartFile file;
  private MultipartFile image;

  @Builder
  public SimpleIdeaBoard(String title, String content, String contact, String classOf,
    Status status,
    List<UserProjectRole> requiredPosition, String phoneNumber, String name,
    UserProjectRole mainRole, MultipartFile file, MultipartFile image) {
    this.title = title;
    this.content = content;
    this.classOf = classOf;
    this.contact = contact;
    this.status = status;
    this.requiredPosition = requiredPosition;
    this.phoneNumber = phoneNumber;
    this.name = name;
    this.mainRole = mainRole;
    this.file = file;
    this.image = image;
  }

}

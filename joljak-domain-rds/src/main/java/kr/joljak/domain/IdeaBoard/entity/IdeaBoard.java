package kr.joljak.domain.IdeaBoard.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.entity.UserProjectRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "IdeaBoards")
public class IdeaBoard extends ExtendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private ProjectStatus status;

  @Column(nullable = false)
  @NotNull
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  private String contact;

  @OneToOne
  @JoinColumn(name = "file_id")
  private Media file;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  @Enumerated(value = EnumType.STRING)
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<UserProjectRole> requiredPosiotions;

  @Builder
  public IdeaBoard(ProjectStatus status, String title, String content, String contact, User user,
    List<UserProjectRole> requiredPositions, Media file) {
    this.status = status;
    this.title = title;
    this.content = content;
    this.contact = contact;
    this.user = user;
    this.requiredPosiotions = requiredPositions;
    this.file = file;
  }

  public static IdeaBoard of(SimpleIdeaBoard simpleIdeaBoard, User user, Media file) {
    return IdeaBoard.builder()
      .status(simpleIdeaBoard.getStatus())
      .title(simpleIdeaBoard.getTitle())
      .content(simpleIdeaBoard.getContent())
      .contact(simpleIdeaBoard.getContact())
      .file(file)
      .user(user)
      .requiredPositions(simpleIdeaBoard.getRequiredPositions())
      .build();
  }

}

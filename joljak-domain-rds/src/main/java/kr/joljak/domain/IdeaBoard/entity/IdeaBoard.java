package kr.joljak.domain.IdeaBoard.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.upload.entity.MediaInfo;
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

  @JsonFormat(shape = Shape.STRING)
  public enum Status {
    complete, Ongoing
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Status status;

  @Column(nullable = false)
  @NotNull
  private String title;

  @Column(nullable = false)
  private String content;

  @Column
  private String contact;
  //TODO : 민성이가 만든 엔티티랑 연결해야함. 객체를 저장하지 말고
  @Column
  private MediaInfo fileInfo;

  @Column
  private MediaInfo imageInfo;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column
  @Enumerated(value = EnumType.STRING)
  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<UserProjectRole> requiredPosiotion;

  @Builder
  public IdeaBoard(Status status, String title, String content, String contact, User user,
    List<UserProjectRole> requiredPosition, MediaInfo fileInfo, MediaInfo imageInfo) {
    this.status = status;
    this.title = title;
    this.content = content;
    this.contact = contact;
    this.user = user;
    this.requiredPosiotion = requiredPosition;
    this.fileInfo = fileInfo;
    this.imageInfo = imageInfo;
  }

}

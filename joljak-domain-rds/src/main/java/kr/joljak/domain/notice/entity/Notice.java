package kr.joljak.domain.notice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notices")
public class Notice extends ExtendEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String classOf;

  @Column(nullable = false)
  private String content;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Notice(User user, String title, String classOf, String content) {
    this.user = user;
    this.title = title;
    this.classOf = classOf;
    this.content = content;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public void setClassOf(String classOf){
    this.classOf = classOf;
  }

  public void setContent(String content){
    this.content = content;
  }

}

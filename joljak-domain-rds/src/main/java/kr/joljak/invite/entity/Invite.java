package kr.joljak.invite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.joljak.common.entity.ExtendEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "expired = false")
@Table(name = "invites")
public class Invite extends ExtendEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String classOf;

  @Column(nullable = false)
  private String inviteCode;

  private boolean expired = false;

  @Builder
  public Invite(String classOf, String inviteCode) {
    this.classOf = classOf;
    this.inviteCode = inviteCode;
  }

  public void expired() {
    this.expired = true;
  }
}

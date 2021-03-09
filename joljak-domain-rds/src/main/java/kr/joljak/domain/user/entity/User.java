package kr.joljak.domain.user.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import kr.joljak.core.security.UserRole;
import kr.joljak.domain.common.entity.ExtendEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends ExtendEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String classOf;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phoneNumber;
  private String instagramId;

  private String kakaoId;

  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private UserProjectRole mainProjectRole;

  @Enumerated(value = EnumType.STRING)
  private UserProjectRole subProjectRole;

  @Column(nullable = false)
  @ElementCollection
  @Enumerated(value = EnumType.STRING)
  private List<UserRole> userRoles;

  @Builder
  public User(String classOf, String password, String name, String phoneNumber,
    List<UserRole> userRoles, UserProjectRole mainProjectRole, UserProjectRole subProjectRole) {
    this.classOf = classOf;
    this.password = password;
    this.name = name;
    this.phoneNumber = phoneNumber;
    this.userRoles = userRoles;
    this.mainProjectRole = mainProjectRole;
    this.subProjectRole = subProjectRole;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public void setInstagramId(String instagramId) {
    this.instagramId = instagramId;
  }

  public void setKakaoId(String kakaoId) {
    this.kakaoId = kakaoId;
  }
}

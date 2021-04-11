package kr.joljak.domain.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.joljak.domain.common.entity.ExtendEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "portfolios")
public class Portfolio extends ExtendEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String link;

  @ManyToOne
  @JoinColumn(name = "profile_id")
  private Profile profile;

  @Builder
  public Portfolio(Long id, String title, String link) {
    this.id = id;
    this.title = title;
    this.link = link;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }
}

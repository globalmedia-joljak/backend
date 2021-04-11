package kr.joljak.domain.user.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import kr.joljak.domain.common.entity.ExtendEntity;
import kr.joljak.domain.upload.entity.Media;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profiles")
public class Profile extends ExtendEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private String content;

  @Size(max = 5)
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Portfolio> portfolioLinks;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "media_id")
  private Media media;

  @Builder
  public Profile(Long id, User user, String content, List<Portfolio> portfolioLinks) {
    this.id = id;
    this.user = user;
    this.content = content;
    this.portfolioLinks = portfolioLinks;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setPortfolioLinks(List<Portfolio> portfolioLinks) {
    this.portfolioLinks = portfolioLinks;
  }
}

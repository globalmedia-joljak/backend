package kr.joljak.domain.user.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import kr.joljak.domain.upload.entity.Media;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profiles")
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private User user;

  private String content;

  @ElementCollection
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<String> portfolioLinks;

  @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
  @JoinColumn(name = "media_id")
  private Media media;

  @Builder
  public Profile(Long id, User user, String content, List<String> portfolioLinks) {
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

  public void setPortfolioLinks(List<String> portfolioLinks) {
    this.portfolioLinks = portfolioLinks;
  }
}

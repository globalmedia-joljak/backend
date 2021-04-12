package kr.joljak.domain.user.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.user.entity.Profile;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleProfile {
  private Long id;
  private String content;
  private List<SimplePortfolio> portfolioLinks;
  private SimpleUser user;
  private MediaInfo mediaInfo;

  @Builder
  public SimpleProfile(Long id, String content, List<SimplePortfolio> portfolioLinks,
      SimpleUser user, MediaInfo mediaInfo) {
    this.id = id;
    this.content = content;
    this.portfolioLinks = portfolioLinks;
    this.user = user;
    this.mediaInfo = mediaInfo;
  }

  public static SimpleProfile of(Profile profile) {
    MediaInfo mediaInfo = null;
    if (profile.getMedia() != null) {
      mediaInfo = MediaInfo.of(profile.getMedia());
    }
    List<SimplePortfolio> profiles = Collections.emptyList();
    if (profile.getPortfolioLinks() != null) {
      profiles = profile.getPortfolioLinks().stream()
        .map(SimplePortfolio::of)
        .collect(Collectors.toList());
    }

    return SimpleProfile.builder()
        .id(profile.getId())
        .mediaInfo(mediaInfo)
        .user(SimpleUser.of(profile.getUser()))
        .content(profile.getContent())
        .portfolioLinks(profiles)
        .build();
  }
}

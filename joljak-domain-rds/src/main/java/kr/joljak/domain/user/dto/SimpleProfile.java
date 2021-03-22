package kr.joljak.domain.user.dto;

import java.util.List;
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
  private List<String> portfolioLinks;
  private SimpleUser user;
  private MediaInfo mediaInfo;

  @Builder
  public SimpleProfile(Long id, String content, List<String> portfolioLinks,
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

    return SimpleProfile.builder()
        .id(profile.getId())
        .mediaInfo(mediaInfo)
        .user(SimpleUser.of(profile.getUser()))
        .content(profile.getContent())
        .portfolioLinks(profile.getPortfolioLinks())
        .build();
  }
}

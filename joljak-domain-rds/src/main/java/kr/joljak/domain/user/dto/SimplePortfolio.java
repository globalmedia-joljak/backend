package kr.joljak.domain.user.dto;

import kr.joljak.domain.user.entity.Portfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimplePortfolio {
  private String title;
  private String link;

  public Portfolio to() {
    return Portfolio.builder()
        .link(link)
        .title(title)
        .build();
  }

  public static SimplePortfolio of(Portfolio portfolio) {
    return new SimplePortfolio(portfolio.getTitle(), portfolio.getLink());
  }
}
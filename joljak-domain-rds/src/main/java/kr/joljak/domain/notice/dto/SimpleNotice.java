package kr.joljak.domain.notice.dto;

import java.time.LocalDateTime;
import kr.joljak.domain.notice.entity.Notice;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleNotice {

  private Long id;
  private String classOf;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime modifiedDate;

  @Builder
  public SimpleNotice(Long id, String classOf, String title, String content,
    LocalDateTime createdDate, LocalDateTime modifiedDate) {
    this.id = id;
    this.title = title;
    this.classOf = classOf;
    this.content = content;
    this.createdDate = createdDate;
    this.modifiedDate = modifiedDate;
  }

  public static SimpleNotice of(Notice notice) {
    return SimpleNotice.builder()
      .id(notice.getId())
      .classOf(notice.getClassOf())
      .title(notice.getTitle())
      .content(notice.getContent())
      .createdDate(notice.getCreatedDate())
      .modifiedDate(notice.getModifiedDate())
      .build();
  }

}

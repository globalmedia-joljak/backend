package kr.joljak.domain.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class FetchPages {

  private FetchPages() {
  }

  public static PageRequest of(int page, int size) {
    if (page < 0) {
      page = 0;
    }
    if (size <= 0) {
      size = 10;
    }
    return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
  }

}

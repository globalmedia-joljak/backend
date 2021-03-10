package kr.joljak.domain.util;

import org.springframework.data.domain.PageRequest;

public class FetchPages {

  private FetchPages() {
  }

  public static final PageRequest of(int page, int size) {
    if (page < 0) {
      page = 0;
    }
    if (size <= 0) {
      size = 10;
    }
    return PageRequest.of(page, size);
  }

}

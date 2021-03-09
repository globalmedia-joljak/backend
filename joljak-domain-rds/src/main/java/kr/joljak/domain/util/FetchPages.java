package kr.joljak.domain.util;

import org.springframework.data.domain.PageRequest;

public class FetchPages {

  private FetchPages() {
  }

  public static final PageRequest of(int page, int size){
    return PageRequest.of(page, size);
  }

}

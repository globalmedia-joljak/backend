package kr.joljak.api.sample.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class SampleController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String test() {

    return "api test";
  }
}

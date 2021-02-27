package kr.joljak.api.sample.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tests")
public class SampleController {

  @ApiOperation("테스트 컨트롤러")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public String test() {

    return "TEST CI/CD";
  }
}

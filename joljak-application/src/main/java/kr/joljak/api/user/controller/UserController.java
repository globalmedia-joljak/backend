package kr.joljak.api.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;

  @ApiOperation("유저 휴대폰 번호 업데이트")
  @PatchMapping("/{classOf}/phonenumber")
  @ResponseStatus(HttpStatus.OK)
  public void updatePhoneNumber(@PathVariable String classOf, @RequestBody String phoneNumber) {
    userService.updatePhoneNumber(classOf, phoneNumber);
  }

  @ApiOperation("유저 인스타 아이디 업데이트")
  @PatchMapping("/{classOf}/istagramid")
  @ResponseStatus(HttpStatus.OK)
  public void updateInstagramId(@PathVariable String classOf, @RequestBody String instagramId) {
    userService.updateInstagramId(classOf, instagramId);
  }

  @ApiOperation("유저 카카오 아이디 업데이트")
  @PatchMapping("/{classOf}/kakaoid")
  @ResponseStatus(HttpStatus.OK)
  public void updateKakaoId(@PathVariable String classOf, @RequestBody String kakaoId) {
    userService.updateKakaoId(classOf, kakaoId);
  }
}

package kr.joljak.api.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.api.user.response.MyPageResponse;
import kr.joljak.domain.user.dto.SimpleUser;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  @ApiOperation("유저 휴대폰 번호 업데이트")
  @PatchMapping("/{classOf}/phonenumber")
  @ResponseStatus(HttpStatus.OK)
  public void updatePhoneNumber(@PathVariable String classOf, @RequestBody(required = false) String phoneNumber) {
    log.info("]-----] UserController::updatePhoneNumber [-----[ classOf : {}, phoneNumber : {}", classOf, phoneNumber);

    userService.updatePhoneNumber(classOf, phoneNumber);
  }

  @ApiOperation("유저 인스타 아이디 업데이트")
  @PatchMapping("/{classOf}/instagramid")
  @ResponseStatus(HttpStatus.OK)
  public void updateInstagramId(@PathVariable String classOf, @RequestBody(required = false) String instagramId) {
    log.info("]-----] UserController::updateInstagramId [-----[ classOf : {}, instagramId : {}", classOf, instagramId);

    userService.updateInstagramId(classOf, instagramId);
  }

  @ApiOperation("유저 카카오 아이디 업데이트")
  @PatchMapping("/{classOf}/kakaoid")
  @ResponseStatus(HttpStatus.OK)
  public void updateKakaoId(@PathVariable String classOf, @RequestBody(required = false) String kakaoId) {
    log.info("]-----] UserController::updateKakaoId [-----[ classOf : {}, kakaoId : {}", classOf, kakaoId);

    userService.updateKakaoId(classOf, kakaoId);
  }

  @ApiOperation("마이페이지 API")
  @GetMapping("/{classOf}")
  @ResponseStatus(HttpStatus.OK)
  public MyPageResponse myPage(@PathVariable String classOf) {
    log.info("]-----] UserController::myPage [-----[ classOf : {}", classOf);

    SimpleUser user = userService.getMyPage(classOf);

    return MyPageResponse.builder()
      .user(user)
      .build();
  }

  @ApiOperation("비밀번호 변경 API")
  @PatchMapping("/{classOf}/password")
  @ResponseStatus(HttpStatus.OK)
  public void updatePassword(@PathVariable String classOf, @RequestBody String password) {
    log.info("]-----] UserController::updatePassword [-----[ classOf : {}, password : {}", classOf, password);

    userService.updatePassword(classOf, password);
  }
}
package kr.joljak.api.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.api.user.request.RegisterProfileRequest;
import kr.joljak.api.user.response.GetProfileResponse;
import kr.joljak.domain.user.dto.SimpleProfile;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.service.ProfileService;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {
  private final ProfileService profileService;
  private final UserService userService;

  @ApiOperation("유저 프로필 등록 API")
  @PostMapping("/{classOf}")
  @ResponseStatus(HttpStatus.CREATED)
  public GetProfileResponse registerProfile(
    @RequestPart(required = false) MultipartFile image,
    @RequestPart RegisterProfileRequest registerProfileRequest,
    @PathVariable String classOf
  ) {
    userService.validExistClassOf(classOf);

    Profile profile = profileService.registerProfile(
      registerProfileRequest.of(),
      registerProfileRequest.getMainRole(),
      registerProfileRequest.getSubRole(),
      image
    );

    return GetProfileResponse.builder()
      .simpleProfile(SimpleProfile.of(profile))
      .build();
  }
}

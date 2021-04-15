package kr.joljak.api.user.controller;

import io.swagger.annotations.ApiOperation;
import kr.joljak.api.user.request.RegisterProfileRequest;
import kr.joljak.api.user.request.UpdateProfileRequest;
import kr.joljak.api.user.response.GetProfileResponse;
import kr.joljak.api.user.response.GetProfilesResponse;
import kr.joljak.domain.user.dto.SimpleProfile;
import kr.joljak.domain.user.entity.Profile;
import kr.joljak.domain.user.entity.UserProjectRole;
import kr.joljak.domain.user.service.ProfileService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profiles")
public class ProfileController {
  
  private final ProfileService profileService;
  
  @ApiOperation("유저 프로필 등록 API")
  @PostMapping("/{classOf}")
  @ResponseStatus(HttpStatus.CREATED)
  public GetProfileResponse registerProfile(
    RegisterProfileRequest registerProfileRequest,
    @PathVariable String classOf
  ) {
    Profile profile = profileService
      .registerProfile(registerProfileRequest.of(classOf, registerProfileRequest.getImage()));
    
    return GetProfileResponse.builder()
      .simpleProfile(SimpleProfile.of(profile))
      .build();
  }
  
  @ApiOperation("유저 프로필 리스트 API")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public GetProfilesResponse getProfiles(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<SimpleProfile> simpleProfilePage = profileService.getProfiles(FetchPages.of(page, size))
      .map(SimpleProfile::of);
    
    return GetProfilesResponse.builder()
      .simpleProfilePage(simpleProfilePage)
      .build();
  }
  
  @ApiOperation("유저 프로필 상세조회 API")
  @GetMapping("/{classOf}")
  @ResponseStatus(HttpStatus.OK)
  public GetProfileResponse getProfile(@PathVariable String classOf) {
    Profile profile = profileService.getProfile(classOf);
    
    return GetProfileResponse.builder()
      .simpleProfile(SimpleProfile.of(profile))
      .build();
  }
  
  @ApiOperation("유저 프로필 검색 API")
  @GetMapping("/search")
  @ResponseStatus(HttpStatus.OK)
  public GetProfilesResponse searchProfiles(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) String classOf,
    @RequestParam(required = false) UserProjectRole role,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size
  ) {
    Page<SimpleProfile> simpleProfilePage = profileService
      .getProfilesByKeyWord(name, classOf, role, FetchPages.of(page, size))
      .map(SimpleProfile::of);
    
    return GetProfilesResponse.builder()
      .simpleProfilePage(simpleProfilePage)
      .build();
  }
  
  @ApiOperation("유저 프로필 업데이트 API")
  @PatchMapping("/{classOf}")
  @ResponseStatus(HttpStatus.OK)
  public GetProfileResponse updateProfile(
    @PathVariable String classOf,
    UpdateProfileRequest updateProfileRequest
  ) {
    Profile profile = profileService
      .updateProfile(updateProfileRequest.of(classOf, updateProfileRequest.getImage()));
    
    return GetProfileResponse.builder()
      .simpleProfile(SimpleProfile.of(profile))
      .build();
  }
  
  @ApiOperation("유저 프로필 삭제 API")
  @DeleteMapping("/{classOf}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteProfile(@PathVariable String classOf) {
    profileService.deleteProfile(classOf);
  }
}

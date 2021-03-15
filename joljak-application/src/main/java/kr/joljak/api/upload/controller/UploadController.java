package kr.joljak.api.upload.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.joljak.api.upload.request.DeleteFileRequest;
import kr.joljak.api.upload.request.UploadRequest;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class UploadController {
  private final UploadService uploadService;
  private final UserService userService;

  @ApiOperation("여러개 파일 업로드")
  @PostMapping("/files")
  @ResponseStatus(HttpStatus.CREATED)
  public List<MediaInfo> uploadFiles(
      @RequestParam List<MultipartFile> files,
      UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadFiles(files, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("파일 업로드")
  @PostMapping("/file")
  @ResponseStatus(HttpStatus.CREATED)
  public MediaInfo uploadFile(
    @RequestParam MultipartFile file,
    UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadFile(file, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("사진 업로드")
  @PostMapping("/images")
  @ResponseStatus(HttpStatus.CREATED)
  public List<MediaInfo> uploadImages(
      @RequestParam List<MultipartFile> images,
      UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadImages(images, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("사진 업로드")
  @PostMapping("/image")
  @ResponseStatus(HttpStatus.CREATED)
  public MediaInfo uploadImage(
      @RequestParam MultipartFile image,
      UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadImage(image, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("파일 삭제")
  @DeleteMapping("/file")
  @ResponseStatus(HttpStatus.OK)
  public void deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
    String classOf = deleteFileRequest.getClassOf();

    userService.validExistClassOf(classOf);
    uploadService.deleteFile(deleteFileRequest.getFileName(), "/" + classOf);
  }
}

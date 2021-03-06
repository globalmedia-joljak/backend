package kr.joljak.api.upload.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import kr.joljak.api.upload.request.DeleteFileRequest;
import kr.joljak.api.upload.request.UploadRequest;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
public class UploadController {

  private final UploadService uploadService;
  private final UserService userService;

  @ApiOperation("여러개 파일 업로드")
  @PostMapping("/files")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Media> uploadFiles(
    @RequestPart(required = false) List<MultipartFile> files,
    @RequestPart UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadFiles(files, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("파일 업로드")
  @PostMapping("/file")
  @ResponseStatus(HttpStatus.CREATED)
  public Media uploadFile(
    @RequestPart(required = false) MultipartFile file,
    @RequestPart UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadFile(file, "/" + uploadRequest.getClassOf(), MediaType.FILE);
  }

  @ApiOperation("사진 여러개 업로드")
  @PostMapping("/images")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Media> uploadImages(
    @RequestPart List<MultipartFile> images,
    @RequestPart UploadRequest uploadRequest
  ) {
    userService.validExistClassOf(uploadRequest.getClassOf());

    return uploadService.uploadImages(images, "/" + uploadRequest.getClassOf());
  }

  @ApiOperation("사진 업로드")
  @PostMapping("/{classOf}/image")
  @ResponseStatus(HttpStatus.CREATED)
  public MediaInfo uploadImage(
    @PathVariable String classOf,
    @RequestPart MultipartFile image
  ) {
    log.info("]-----] UploadController::uploadImage [-----[ classOf : {}, image : {}, size : {}"
      , classOf, image.getName(), image.getSize());

    userService.validExistClassOf(classOf);
    Media media = uploadService.uploadImage(image, "/" + classOf);

    return MediaInfo.of(media);
  }

  @ApiOperation("파일 삭제")
  @DeleteMapping("/file")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteFile(@RequestBody DeleteFileRequest deleteFileRequest) {
    String classOf = deleteFileRequest.getClassOf();

    userService.validExistClassOf(classOf);
    uploadService.deleteFile(deleteFileRequest.getFileName(), "/" + classOf);
  }
}

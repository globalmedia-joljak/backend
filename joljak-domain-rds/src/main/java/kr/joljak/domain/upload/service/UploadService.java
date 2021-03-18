package kr.joljak.domain.upload.service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {
  private final S3Service s3Service;

  public List<MediaInfo> uploadImages(List<MultipartFile> files, String path) {
    List<MediaInfo> mediaInfos = new ArrayList<>();

    for (MultipartFile file : files) {
      validateImage(file);
      mediaInfos.add(uploadFile(file, path));
    }

    return mediaInfos;
  }

  public MediaInfo uploadImage(MultipartFile file, String path) {
    validateImage(file);
    return uploadFile(file, path);
  }

  public List<MediaInfo> uploadFiles(List<MultipartFile> files, String path) {
    List<MediaInfo> mediaInfos = new ArrayList<>();

    for (MultipartFile file : files) {
      mediaInfos.add(uploadFile(file, path));
    }

    return mediaInfos;
  }

  public MediaInfo uploadFile(MultipartFile file, String path) {
    String originalName = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalName);
    String modifyName = getEraseExtensionFileName(originalName) + getModifyName(fileExtension);
    String fullPath = s3Service.getBucket() + path;

    log.debug("-----] S3Service::getMediaInfo.originalFileName [-----[ " + originalName);

    String url = s3Service.uploadFile(file, modifyName, fullPath);

    return MediaInfo.builder()
      .originalName(originalName)
      .modifyName(modifyName)
      .fileExtension(fileExtension)
      .url(url)
      .fullPath(fullPath)
      .build();
  }

  private void validateImage(MultipartFile file) {
    BufferedImage bi;

    try {
      bi = ImageIO.read(file.getInputStream());
    } catch (Exception e) {
      throw new FileIsNotImageException();
    }

    if (bi == null) {
      throw new FileIsNotImageException();
    }
  }

  private String getFileExtension(String fileName) {
    int index = fileName.lastIndexOf(".");

    return fileName.substring(index + 1);
  }

  private String getEraseExtensionFileName(String fileName) {
    int index = fileName.lastIndexOf(".");

    return fileName.substring(0, index);
  }

  private String getModifyName(String fileExtension) {
    return "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "." + fileExtension;
  }

  public void deleteFile(String modifyFileName, String fullPath) {
    s3Service.deleteFile(modifyFileName, fullPath);
  }
}

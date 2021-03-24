package kr.joljak.domain.upload.service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadService {
  private final S3Service s3Service;
  private final MediaService mediaService;

  @Transactional
  public List<Media> uploadImages(List<MultipartFile> files, String path) {
    List<Media> medias = new ArrayList<>();

    for (MultipartFile file : files) {
      validateImage(file);
      medias.add(uploadFile(file, path, MediaType.IMAGE));
    }

    return medias;
  }

  @Transactional
  public Media uploadImage(MultipartFile file, String path) {
    validateImage(file);
    return uploadFile(file, path, MediaType.IMAGE);
  }

  @Transactional
  public List<Media> uploadFiles(List<MultipartFile> files, String path) {
    List<Media> medias = new ArrayList<>();

    for (MultipartFile file : files) {
      medias.add(uploadFile(file, path, MediaType.FILE));
    }

    return medias;
  }

  @Transactional
  public Media uploadFile(MultipartFile file, String path, MediaType mediaType) {
    String originalName = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalName);
    String modifyName = getEraseExtensionFileName(originalName) + getModifyName(fileExtension);
    String fullPath = s3Service.getBucket() + path;

    log.debug("-----] S3Service::getMediaInfo.originalFileName [-----[ " + originalName);

    String url = s3Service.uploadFile(file, modifyName, fullPath);

    Media media = Media.builder()
      .mediaType(mediaType)
      .fileExtension(fileExtension)
      .fullPath(fullPath)
      .modifyName(modifyName)
      .originalName(originalName)
      .uploadToS3(true)
      .url(url)
      .build();

    return mediaService.saveMedia(media);
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

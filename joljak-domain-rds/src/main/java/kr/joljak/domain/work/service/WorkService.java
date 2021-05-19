package kr.joljak.domain.work.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.repository.MediaRepository;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.ProjectCategory;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.exception.WorkNotFoundException;
import kr.joljak.domain.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkService {
  
  private final WorkRepository workRepository;
  private final UploadService uploadService;
  private final UserService userService;
  private final MediaRepository mediaRepository;
  
  @Transactional
  public Work addWork(SimpleWork simpleWork) {
    log.info( "]-----] WorkService::addWork [-----[ classOf : {}", AuthenticationUtils.getClassOf());

    List<MultipartFile> images = simpleWork.getImages();
    User user = userService.getUserByAuthentication();
    simpleWork.setUser(user);
    
    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + user.getClassOf());
    }
    
    Media file = null;
    if (simpleWork.getFile() != null) {
      file = uploadService
        .uploadFile(simpleWork.getFile(), "/" + user.getClassOf(), MediaType.FILE);
    }
    
    Work work = Work.of(simpleWork, imageList, file);
    if (!(simpleWork.getTeamVideoUrl() == null)) {
      work.setTeamVideoUrl(convertYoutubeUrl(simpleWork.getTeamVideoUrl()));
    }

    return workRepository.save(work);
  }
  
  @Transactional
  public Work updateWorkById(Long id, UpdateWork updateWork) {
    log.info( "]-----] WorkService::updateWorkById [-----[ id : {}, classOf : {}", id, AuthenticationUtils.getClassOf());

    List<MultipartFile> images = updateWork.getImages();
    Work work = getWorkById(id);
    List<Long> deleteImageIds = work.getImages()
      .stream()
      .map(Media::getId)
      .collect(Collectors.toList());
    
    String classOf = work.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);
    
    work.setWorkName(updateWork.getWorkName());
    work.setTeamName(updateWork.getTeamName());
    work.setTeamMember(updateWork.getTeamMember());
    work.setContent(updateWork.getContent());
    work.setExhibitedYear(updateWork.getExhibitedYear());
    work.setProjectCategory(updateWork.getProjectCategory());
    work.setTeamVideoUrl(convertYoutubeUrl(updateWork.getTeamVideoUrl()));
    
    deleteImageByModifyFileName(work, updateWork.getDeleteImagesName());
    addImages(images, work);
    
    if (work.getImages().isEmpty()) {
      work.setImages(null);
    }
    
    mediaRepository.deleteByIdIn(deleteImageIds);
    updateFile(updateWork, work);
    
    return work;
  }

  private String convertYoutubeUrl(String url) {
    if (url == null) return "";

    String convertUrl = url;
    if (url.contains("watch?")) {
      convertUrl = url.split("watch?")[1]
          .split("v=")[1]
          .split("&")[0];
    }
    else if (url.contains("https://youtu.be/")) {
      convertUrl = url.split("https://youtu.be/")[1];
    }
    else if(url.contains("<iframe")) {
      convertUrl = url.split("src=")[1]
          .split(" ")[0]
          .replaceAll("\"", "")
          .split("https://www.youtube.com/embed/")[1];
    }

    log.info( "]-----] WorkService::convertYoutubeUrl [-----[ {} => {}", url, convertUrl);

    return convertUrl;
  }

  @Transactional(readOnly = true)
  public Page<Work> getWorksByPage(PageRequest pageRequest) {
    return workRepository.findAll(pageRequest);
  }
  
  @Transactional(readOnly = true)
  public Work getWorkById(Long id) {
    return workRepository.findById(id)
      .orElseThrow(() -> new WorkNotFoundException(id));
  }
  
  @Transactional
  public void deleteWorkById(Long id) {
    log.info( "]-----] WorkService::deleteWorkById [-----[ id : {}, classOf : {}", id, AuthenticationUtils.getClassOf());

    Work work = getWorkById(id);
    String classOf = work.getUser().getClassOf();
    userService.validExistClassOf(classOf);
    
    List<String> deleteFileName = work.getImages().stream()
      .map(Media::getModifyName)
      .collect(Collectors.toList());
    
    deleteImageByModifyFileName(work, deleteFileName);
    
    work.setMedia(null);
    
    workRepository.delete(work);
  }
  
  @Transactional(readOnly = true)
  public Page<Work> getWorkByExhibitedYearAndCategory(ProjectCategory projectCategory,
    String exhibitedYear,
    PageRequest pageRequest) {
    
    return workRepository
      .findByProjectCategoryOrExhibitedYear(projectCategory, exhibitedYear, pageRequest);
  }
  
  private void deleteImageByModifyFileName(Work work, List<String> deleteFileNames) {
    log.info( "]-----] WorkService::deleteImageByModifyFileName [-----[ classOf : {}"
      , work.getUser().getClassOf());

    List<Media> mediaList = work.getImages();
    
    if (mediaList != null && deleteFileNames != null) {
      
      Map<String, Media> imageHash = new HashMap<>();
      
      for (Media image : mediaList) {
        imageHash.put(image.getModifyName(), image);
      }
      
      checkDeleteFileNameExist(imageHash, deleteFileNames);
      
      for (String deleteImage : deleteFileNames) {
        work.getImages().remove(imageHash.get(deleteImage));
        uploadService.deleteFile(deleteImage, "/" + work.getUser().getClassOf());
      }
    }
  }
  
  private void addImages(List<MultipartFile> images, Work work) {
    
    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + work.getUser().getClassOf());
      
      for (Media image : imageList) {
        work.getImages().add(image);
      }
    }
  }
  
  private void checkDeleteFileNameExist(Map<String, Media> imageHash,
    List<String> deleteFileNames) {
    
    for (String deleteImage : deleteFileNames) {
      if (!imageHash.containsKey(deleteImage)) {
        throw new NotMatchingFileNameException("image name does not match when you delete.");
      }
    }
  }
  
  private void updateFile(UpdateWork updateWork, Work work) {
    if (updateWork.getDeleteFileName() != null) {
      Media media = work.getMedia();
      if (media != null && !media.getModifyName().equals(updateWork.getDeleteFileName())) {
        throw new NotMatchingFileNameException("file name does not match when you delete.");
      }
      work.setMedia(null);
      uploadService.deleteFile(media.getModifyName(), "/" + work.getUser().getClassOf());
    }
    
    if (updateWork.getFile() != null) {
      Media mediaFile = uploadService
        .uploadFile(updateWork.getFile(), "/" + work.getUser().getClass(), MediaType.FILE);
      work.setMedia(mediaFile);
    }
  }
  
  public List<String> getExhibitedYear() {
    List<String> exhibitedYear = new ArrayList<>();
    int thisYear = LocalDateTime.now().getYear();
    for (int i = 2019; i <= thisYear; ++i) {
      exhibitedYear.add(Integer.toString(i));
    }
    return exhibitedYear;
  }
}

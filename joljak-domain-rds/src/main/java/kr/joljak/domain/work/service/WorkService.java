package kr.joljak.domain.work.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class WorkService {
  
  private final WorkRepository workRepository;
  private final UploadService uploadService;
  private final UserService userService;
  
  @Transactional
  public Work addWork(SimpleWork simpleWork) {
    
    List<MultipartFile> images = simpleWork.getImages();
    User user = userService.getUserByAuthentication();
    simpleWork.setUser(user);
    
    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + user.getClassOf());
    }
    
    Work work = Work.of(simpleWork, imageList);
    
    return workRepository.save(work);
  }
  
  @Transactional
  public Work updateWorkById(Long id, UpdateWork updateWork) {
  
    List<MultipartFile> images = updateWork.getImages();
    Work work = getWorkById(id);
    
    String classOf = work.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);
    
    work.setWorkName(updateWork.getWorkName());
    work.setTeamName(updateWork.getTeamName());
    work.setTeamMember(updateWork.getTeamMember());
    work.setContent(updateWork.getContent());
    work.setExhibitedYear(updateWork.getExhibitedYear());
    work.setProjectCategory(updateWork.getProjectCategory());
    work.setTeamVideoUrl(updateWork.getTeamVideoUrl());
    
    deleteImageByModifyFileName(work, updateWork.getDeleteFileName());
    addImages(images, work);
    
    return work;
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
    Work work = getWorkById(id);
    String classOf = work.getUser().getClassOf();
    userService.validExistClassOf(classOf);
    
    List<String> deleteFileName = work.getImages().stream()
      .map(Media::getModifyName)
      .collect(Collectors.toList());
    
    deleteImageByModifyFileName(work, deleteFileName);
    
    workRepository.delete(work);
  }
  
  @Transactional(readOnly = true)
  public Page<Work> getWorkByExhibitedYearAndCategory(ProjectCategory projectCategory, String exhibitedYear,
    PageRequest pageRequest) {
    if (projectCategory != null && exhibitedYear != null) {
      return workRepository
        .findAllByProjectCategoryAndExhibitedYearContaining(projectCategory, exhibitedYear, pageRequest);
    } else if (projectCategory != null && exhibitedYear == null) {
      return workRepository.findAllByProjectCategory(projectCategory, pageRequest);
    } else if (projectCategory == null && exhibitedYear != null) {
      return workRepository.findAllByExhibitedYear(exhibitedYear, pageRequest);
    } else {
      return getWorksByPage(pageRequest);
    }
  }
  
  @Transactional
  private void deleteImageByModifyFileName(Work work, List<String> deleteFileNames) {
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
  
  private void checkDeleteFileNameExist(Map<String, Media> imageHash,
    List<String> deleteFileNames) {
    
    for (String deleteImage : deleteFileNames) {
      if (!imageHash.containsKey(deleteImage)) {
        throw new NotMatchingFileNameException("image name does not match when you delete.");
      }
    }
  }
  
  @Transactional
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
}

package kr.joljak.domain.work.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.util.FetchPages;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.dto.UpdateWork;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.exception.WorkNotFoundException;
import kr.joljak.domain.work.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class WorkService {

  private final WorkRepository workRepository;
  private final UploadService uploadService;
  private final UserService userService;

  @Transactional
  public Work addWork(SimpleWork simpleWork, List<MultipartFile> images) {

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
  public Work updateWorkById(Long id, UpdateWork updateWork, List<MultipartFile> images) {

    Work work = getWorkById(id);

    String classOf = work.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);

    work.setWorkName(updateWork.getWorkName());
    work.setTeamName(updateWork.getTeamName());
    work.setTeamMember(updateWork.getTeamMember());
    work.setContent(updateWork.getContent());
    work.setTeamVideoUrl(updateWork.getTeamVideoUrl());

    deleteImageByModifyFileName(work, updateWork);
    addImages(images, work);

    return work;
  }

  @Transactional(readOnly = true)
  public Page<Work> getWorksByPage(int page, int size) {
    return workRepository.findAll(FetchPages.of(page, size));
  }

  @Transactional(readOnly = true)
  public Work getWorkById(Long id) {
    return workRepository.findById(id)
      .orElseThrow(() -> new WorkNotFoundException(id));
  }

  @Transactional
  private void deleteImageByModifyFileName(Work work, UpdateWork updateWork) {
    List<Media> mediaList = work.getImages();

    if (mediaList != null && updateWork.getDeleteFileName() != null) {

      Map<String, Media> imageHash = new HashMap<>();

      for (Media image : mediaList) {
        imageHash.put(image.getModifyName(), image);
      }

      checkDeleteFileNameExist(imageHash, updateWork.getDeleteFileName());

      for (String deleteImage : updateWork.getDeleteFileName()) {
        work.getImages().remove(imageHash.get(deleteImage));
        uploadService.deleteFile(deleteImage, "/" + work.getUser().getClassOf());
      }
    }
  }

  private void checkDeleteFileNameExist(Map<String, Media> imageHash, List<String> deleteFileName) {

    for (String deleteImage : deleteFileName) {
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

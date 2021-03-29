package kr.joljak.domain.work.service;

import java.util.List;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.util.FetchPages;
import kr.joljak.domain.work.dto.SimpleWork;
import kr.joljak.domain.work.entity.Work;
import kr.joljak.domain.work.exception.WorkNotFounException;
import kr.joljak.domain.work.repository.WorkRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.service.UserService;
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
  public Work addTeam(SimpleWork simpleWork, List<MultipartFile> images) {

    User user = getUserByAuthentication();
    simpleWork.setUser(user);

    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + user.getClassOf());
    }

    Work work = Work.of(simpleWork, imageList);

    return workRepository.save(work);
  }

  public User getUserByAuthentication() {
    String authenticationClassOf = AuthenticationUtils.getClassOf();

    return userService.getUserByClassOf(authenticationClassOf);
  }

  @Transactional(readOnly = true)
  public Page<Work> getWorksByPage(int page, int size) {
    return workRepository.findAll(FetchPages.of(page, size));
  }

  public Work getWorkById(Long id) {
    return workRepository.findById(id)
      .orElseThrow(() -> new WorkNotFounException(id));
  }
}

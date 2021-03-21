package kr.joljak.domain.IdeaBoard.service;

import java.util.List;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.repository.IdeaBoardRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class IdeaBoardService {

  private final UserService userService;
  private final UploadService uploadService;
  private final IdeaBoardRepository ideaBoardRepository;

  @Transactional
  public IdeaBoard addIdeaBoard(SimpleIdeaBoard simpleIdeaBoard, MultipartFile file) {

    User user = getUserByAuthentication();

    Media mediaFile = uploadService
      .uploadFile(file, "/" + user.getClassOf(), MediaType.FILE);

    IdeaBoard ideaBoard = IdeaBoard.of(simpleIdeaBoard, user, mediaFile);

    return ideaBoardRepository.save(ideaBoard);
  }

  public User getUserByAuthentication() {
    String authenticationClassOf = AuthenticationUtils.getClassOf();

    return userService.getUserByClassOf(authenticationClassOf);
  }

  @Transactional(readOnly = true)
  public List<IdeaBoard> getIdeaBoardsByPage(int page, int size) {
    Pageable pageable = FetchPages.of(page, size);
    return ideaBoardRepository.findAll(pageable).getContent();
  }
}

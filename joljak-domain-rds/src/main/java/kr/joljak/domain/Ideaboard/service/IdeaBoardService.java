package kr.joljak.domain.Ideaboard.service;

import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.Ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.repository.IdeaBoardRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import kr.joljak.domain.util.FetchPages;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
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
  public kr.joljak.domain.Ideaboard.entity.IdeaBoard addIdeaBoard(
    SimpleIdeaBoard simpleIdeaBoard, MultipartFile file) {

    User user = getUserByAuthentication();

    Media mediaFile = null;
    if(file != null){
      mediaFile = uploadService
        .uploadFile(file, "/" + user.getClassOf(), MediaType.FILE);
    }

    IdeaBoard ideaBoard = IdeaBoard.builder()
      .status(simpleIdeaBoard.getStatus())
      .title(simpleIdeaBoard.getTitle())
      .content(simpleIdeaBoard.getContent())
      .contact(simpleIdeaBoard.getContact())
      .file(mediaFile)
      .user(user)
      .requiredPositions(simpleIdeaBoard.getRequiredPositions())
      .build();

//    IdeaBoard ideaBoard = IdeaBoard.of(simpleIdeaBoard, user, mediaFile);

    return ideaBoardRepository.save(ideaBoard);
  }

  public User getUserByAuthentication() {
    String authenticationClassOf = AuthenticationUtils.getClassOf();

    return userService.getUserByClassOf(authenticationClassOf);
  }

  @Transactional(readOnly = true)
  public Page<IdeaBoard> getIdeaBoardsByPage(int page, int size) {

    return ideaBoardRepository.findAll(FetchPages.of(page, size));
  }
}

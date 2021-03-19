package kr.joljak.domain.IdeaBoard.service;

import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.repository.IdeaBoardRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
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

    userService.validAuthenticationClassOf(simpleIdeaBoard.getClassOf());
    User user = userService.getUserByClassOf(simpleIdeaBoard.getClassOf());

    Media mediaFile = uploadService
      .uploadFile(file, "/" + simpleIdeaBoard.getClassOf(), MediaType.FILE);

    IdeaBoard ideaBoard = IdeaBoard.builder()
      .status(simpleIdeaBoard.getStatus())
      .title(simpleIdeaBoard.getTitle())
      .content(simpleIdeaBoard.getContent())
      .contact(simpleIdeaBoard.getContact())
      .file(mediaFile)
      .user(user)
      .requiredPosition(simpleIdeaBoard.getRequiredPosition())
      .build();

    return ideaBoardRepository.save(ideaBoard);
  }

}

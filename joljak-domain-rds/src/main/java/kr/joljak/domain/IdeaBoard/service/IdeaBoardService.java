package kr.joljak.domain.IdeaBoard.service;

import kr.joljak.domain.IdeaBoard.dto.SimpleIdeaBoard;
import kr.joljak.domain.IdeaBoard.entity.IdeaBoard;
import kr.joljak.domain.IdeaBoard.repository.IdeaBoardRepository;
import kr.joljak.domain.upload.entity.MediaInfo;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IdeaBoardService {

  private final UserService userService;
  private final UploadService uploadService;
  private final IdeaBoardRepository ideaBoardRepository;

  @Transactional
  public IdeaBoard addIdeaBoard(SimpleIdeaBoard simpleIdeaBoard) {

    userService.validAuthenticationClassOf(simpleIdeaBoard.getClassOf());
    User user = userService.getUserByClassOf(simpleIdeaBoard.getClassOf());

    MediaInfo file = uploadService
      .uploadFile(simpleIdeaBoard.getFile(), "/" + simpleIdeaBoard.getClassOf());
    MediaInfo image = uploadService
      .uploadImage(simpleIdeaBoard.getImage(), "/" + simpleIdeaBoard.getClassOf());

    IdeaBoard ideaBoard = IdeaBoard.builder()
      .status(simpleIdeaBoard.getStatus())
      .title(simpleIdeaBoard.getTitle())
      .content(simpleIdeaBoard.getContent())
      .contact(simpleIdeaBoard.getContact())
      .fileInfo(file)
      .imageInfo(image)
      .user(user)
      .requiredPosition(simpleIdeaBoard.getRequiredPosition())
      .build();

    return ideaBoardRepository.save(ideaBoard);
  }

}

package kr.joljak.domain.Ideaboard.service;

import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.Ideaboard.dto.SimpleIdeaBoard;
import kr.joljak.domain.Ideaboard.entity.IdeaBoard;
import kr.joljak.domain.Ideaboard.exception.IdeaBoardNotFoundException;
import kr.joljak.domain.Ideaboard.repository.IdeaBoardRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
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
  public IdeaBoard addIdeaBoard(

    SimpleIdeaBoard simpleIdeaBoard, MultipartFile file) {

    User user = getUserByAuthentication();

    Media mediaFile = null;
    if (file != null) {
      mediaFile = uploadService
        .uploadFile(file, "/" + user.getClassOf(), MediaType.FILE);
    }

    IdeaBoard ideaBoard = IdeaBoard.of(simpleIdeaBoard, user, mediaFile);

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

  @Transactional(readOnly = true)
  public IdeaBoard getIdeaBoardsById(Long id) {

    return ideaBoardRepository.findById(id)
      .orElseThrow(() -> new IdeaBoardNotFoundException(id));
  }

  @Transactional
  public IdeaBoard updateIdeaBoardById(Long id,
    SimpleIdeaBoard simpleIdeaBoard, MultipartFile file) {

    IdeaBoard ideaBoard = getIdeaBoardsById(id);
    String classOf = ideaBoard.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);

    ideaBoard.setTitle(simpleIdeaBoard.getTitle());
    ideaBoard.setContent(simpleIdeaBoard.getContent());
    ideaBoard.setStatus(simpleIdeaBoard.getStatus());
    ideaBoard.setContact(simpleIdeaBoard.getContact());
    ideaBoard.setRequiredPosiotions(simpleIdeaBoard.getRequiredPositions());

    if (simpleIdeaBoard.getDeleteFileName() != null) {
      Media media = ideaBoard.getMedia();

      if (media != null && !media.getModifyName()
        .equals(simpleIdeaBoard.getDeleteFileName())) {
        throw new NotMatchingFileNameException("file name does not match when you delete.");
      }
      ideaBoard.setMedia(null);
      uploadService.deleteFile(media.getModifyName(), "/" + ideaBoard.getUser().getClassOf());
    }

    if (file != null) {
      Media mediaFile = uploadService
        .uploadFile(file, "/" + classOf, MediaType.FILE);
      ideaBoard.setMedia(mediaFile);
    }

    return ideaBoard;
  }

  @Transactional
  public void deleteIdeaBoardById(Long id) {
    IdeaBoard ideaBoard = getIdeaBoardsById(id);
    String classOf = ideaBoard.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);
    ideaBoard.setMedia(null);

    ideaBoardRepository.delete(ideaBoard);
  }
}

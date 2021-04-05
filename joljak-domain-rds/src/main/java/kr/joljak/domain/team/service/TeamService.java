package kr.joljak.domain.team.service;

import java.util.List;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.repository.TeamRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TeamService {

  private final UserService userService;
  private final UploadService uploadService;
  private final TeamRepository teamRepository;

  @Transactional
  public Team addTeam(SimpleTeam simpleTeam) {
    User user = userService.getUserByAuthentication();
    simpleTeam.setAuthor(user);

    List<Media> imageList = null;
    if (simpleTeam.getImages() != null) {
      imageList = uploadService
        .uploadImages(simpleTeam.getImages(), "/" + user.getClassOf());
    }

    Team team = Team.of(simpleTeam, imageList);

    return teamRepository.save(team);
  }
}

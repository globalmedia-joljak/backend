package kr.joljak.domain.team.service;

import java.util.List;
import kr.joljak.core.security.AuthenticationUtils;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.repository.TeamRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TeamService {

  private final TeamRepository teamRepository;
  private final UploadService uploadService;

  @Transactional
  public Team addTeam(SimpleTeam simpleTeam, List<MultipartFile> images) {

    String authenticationClassOf = AuthenticationUtils.getClassOf();

    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + authenticationClassOf);
    }

    Team team = Team.of(simpleTeam, imageList);

    return teamRepository.save(team);
  }

}

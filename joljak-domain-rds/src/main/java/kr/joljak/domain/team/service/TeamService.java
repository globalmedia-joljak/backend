package kr.joljak.domain.team.service;

import java.util.List;
import java.util.Map;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.exception.TeamNotFoundException;
import kr.joljak.domain.team.repository.TeamRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    
    Media file = null;
    if(simpleTeam.getFile() != null) {
      file = uploadService
        .uploadFile(simpleTeam.getFile(), "/" + user.getClassOf(), MediaType.FILE);
    }
    
    Team team = Team.of(simpleTeam, file);
    
    return teamRepository.save(team);
  }
  
  @Transactional(readOnly = true)
  public Page<Team> getTeams(PageRequest pageRequest) {
    return teamRepository.findAll(pageRequest);
  }
  
  @Transactional(readOnly = true)
  public Team getTeam(Long id) {
    return teamRepository.findById(id)
      .orElseThrow(TeamNotFoundException::new);
  }
  
  @Transactional
  public Team updateTeam(Long id, UpdateTeam updateTeam) {
    Team team = getTeamById(id);
    
    String classOf = team.getUser().getClassOf();
    userService.validAuthenticationClassOf(classOf);
    
    team.setTeamName(updateTeam.getTeamName());
    team.setCategory(updateTeam.getCategory());
    team.setMediaArtMember(updateTeam.getMediaArtMember());
    team.setDesignerMember(updateTeam.getDesignerMember());
    team.setDeveloperMember(updateTeam.getDeveloperMember());
    team.setPlannerMember(updateTeam.getPlannerMember());
    
    if(updateTeam.getDeleteFileName() != null){
      Media media = team.getMedia();
      
      if(media != null && !media.getModifyName()
        .equals(updateTeam.getDeleteFileName())) {
        throw new NotMatchingFileNameException("file name does not match when you delete.");
      }
      team.setMedia(null);
      uploadService.deleteFile(media.getModifyName(), "/" + team.getUser().getClassOf());
    }
    
    if(updateTeam.getFile() != null){
      Media mediaFile = uploadService
        .uploadFile(updateTeam.getFile(), "/" + team.getUser().getClassOf(), MediaType.FILE);
      team.setMedia(mediaFile);
    }
    
    return team;
  }
  
  @Transactional(readOnly = true)
  public Team getTeamById(Long id) {
    return teamRepository.findById(id)
      .orElseThrow(() -> new TeamNotFoundException());
  }
  
  @Transactional
  public void deleteTeamById(Long id) {
    Team team = getTeamById(id);
    String classOf = team.getUser().getClassOf();
    userService.validExistClassOf(classOf);
    
    team.setMedia(null);
    
    teamRepository.delete(team);
  }
}

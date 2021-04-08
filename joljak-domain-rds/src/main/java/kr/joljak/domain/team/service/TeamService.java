package kr.joljak.domain.team.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kr.joljak.domain.team.dto.SimpleTeam;
import kr.joljak.domain.team.dto.UpdateTeam;
import kr.joljak.domain.team.entity.Team;
import kr.joljak.domain.team.exception.TeamNotFoundException;
import kr.joljak.domain.team.repository.TeamRepository;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.exception.NotMatchingFileNameException;
import kr.joljak.domain.upload.service.UploadService;
import kr.joljak.domain.user.entity.User;
import kr.joljak.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    
    deleteImageByModifyFileName(team, updateTeam.getDeleteFileName());
    addImages(updateTeam.getImages(), team);
    
    return team;
  }
  
  @Transactional
  private void deleteImageByModifyFileName(Team team, List<String> deleteFileNames) {
    List<Media> mediaList = team.getImages();
    
    if (mediaList != null && deleteFileNames != null) {
      Map<String, Media> imageHash = new HashMap<>();
      
      for (Media image : mediaList) {
        imageHash.put(image.getModifyName(), image);
      }
      
      checkDeleteFileNameExist(imageHash, deleteFileNames);
      
      for (String deleteImage : deleteFileNames) {
        team.getImages().remove(imageHash.get(deleteImage));
        uploadService.deleteFile(deleteImage, "/" + team.getUser().getClassOf());
      }
    }
  }
  
  private void checkDeleteFileNameExist(Map<String, Media> imageHash,
    List<String> deleteFileNames) {
    
    for (String deleteImage : deleteFileNames) {
      if (!imageHash.containsKey(deleteImage)) {
        throw new NotMatchingFileNameException("image name does not match when you delete.");
      }
    }
  }
  
  private void addImages(List<MultipartFile> images, Team team) {
    
    List<Media> imageList = null;
    if (images != null) {
      imageList = uploadService
        .uploadImages(images, "/" + team.getUser().getClassOf());
      
      for (Media image : imageList) {
        team.getImages().add(image);
      }
    }
  }
  
  @Transactional(readOnly = true)
  public Team getTeamById(Long id) {
    return teamRepository.findById(id)
      .orElseThrow(() -> new TeamNotFoundException());
  }
}

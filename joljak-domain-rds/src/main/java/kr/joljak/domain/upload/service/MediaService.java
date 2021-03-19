package kr.joljak.domain.upload.service;

import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.exception.MediaNotFoundException;
import kr.joljak.domain.upload.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService {
  private final MediaRepository mediaRepository;

  @Transactional
  public Media saveMedia(Media media) {
    return mediaRepository.save(media);
  }

  @Transactional(readOnly = true)
  public Media getMedia(Long mediaId) {
    return mediaRepository.findById(mediaId)
      .orElseThrow(MediaNotFoundException::new);
  }

  @Transactional
  public void deleteMedia(Long mediaId) {
    mediaRepository.deleteById(mediaId);
  }
}

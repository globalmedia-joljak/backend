package kr.joljak.domain.media;

import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.MediaNotFoundException;
import kr.joljak.domain.upload.service.MediaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class MediaServiceTest extends CommonDomainTest {
  @Autowired
  private MediaService mediaService;

  private static final String TEST_IMAGE_URL
    = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTzX5CFgFWvaBYfqodARNRRgelFokhsxKGsyw&usqp=CAU";

  @Test
  public void saveMedia_Success() {
    // given
    Media media = createMockMedia();

    // when
    media = mediaService.saveMedia(media);

    // then
    Assertions.assertThat(media.getId()).isNotNull();
  }

  @Test
  public void getMedia_Success() {
    // given
    Media media = mediaService.saveMedia(createMockMedia());

    // when
    Media getMedia = mediaService.getMedia(media.getId());

    // then
    Assertions.assertThat(media.getId()).isEqualTo(getMedia.getId());
  }

  @Test(expected = MediaNotFoundException.class)
  public void deleteMedia_Success() {
    // given
    Media media = mediaService.saveMedia(createMockMedia());

    // when
    mediaService.deleteMedia(media.getId());

    // then
    mediaService.getMedia(media.getId());
  }

  public Media createMockMedia() {
    return Media.builder()
      .mediaType(MediaType.FILE)
      .fileExtension(null)
      .fullPath(null)
      .modifyName(null)
      .originalName(null)
      .uploadToS3(false)
      .url(TEST_IMAGE_URL)
      .build();
  }

}

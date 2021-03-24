package kr.joljak.domain.upload;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.entity.Media;
import kr.joljak.domain.upload.entity.MediaType;
import kr.joljak.domain.upload.exception.FileIsNotImageException;
import kr.joljak.domain.upload.service.UploadService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.multipart.MultipartFile;

public class UploadServiceTest extends CommonDomainTest {
  @Autowired
  private UploadService uploadService;

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadFiles_Success() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> textFiles = new ArrayList<>(
      Arrays.asList(
        createMockTextFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );

    // when
    List<Media> media = uploadService.uploadFiles(textFiles, path);

    // then
    assertEquals(media.size(), textFiles.size());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadFile_Success() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);

    // when
    Media media = uploadService.uploadFile(textFile, path, MediaType.FILE);

    // then
    Assertions.assertThat(media).isNotNull();
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImages_Success() throws Exception{
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> imageFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockImageFile("test" + nextId++)
      )
    );

    // when
    List<Media> medias = uploadService.uploadImages(imageFiles, path);

    // then
    Assertions.assertThat(medias).isNotNull();
  }

  @Test(expected = FileIsNotImageException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImages_Fail_FileIsNotImageException() throws Exception{
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    List<MultipartFile> imageFiles = new ArrayList<>(
      Arrays.asList(
        createMockImageFile("test" + nextId++),
        createMockTextFile("test" + nextId++)
      )
    );

    // when
    List<Media> medias = uploadService.uploadImages(imageFiles, path);

    // then
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImage_Success() throws Exception{
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile imageFile = createMockImageFile("test" + nextId++);

    // when
    Media media = uploadService.uploadImage(imageFile, path);

    // then
    Assertions.assertThat(media).isNotNull();
  }

  @Test(expected = FileIsNotImageException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImage_Fail_FileIsNotImageException() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);

    // when
    Media media = uploadService.uploadImage(textFile, path);

    // then
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteFile_Success() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);
    Media media = uploadService.uploadFile(textFile, path, MediaType.FILE);

    // when, then
    uploadService.deleteFile(media.getModifyName(), "/" + TEST_USER_CLASS_OF);
  }
}

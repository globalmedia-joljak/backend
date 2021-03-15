package kr.joljak.domain.upload;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.joljak.domain.common.CommonDomainTest;
import kr.joljak.domain.upload.entity.MediaInfo;
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
    List<MediaInfo> mediaInfos = uploadService.uploadFiles(textFiles, path);

    // then
    assertEquals(mediaInfos.size(), textFiles.size());
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadFile_Success() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);

    // when
    MediaInfo mediaInfo = uploadService.uploadFile(textFile, path);

    // then
    Assertions.assertThat(mediaInfo).isNotNull();
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
    List<MediaInfo> mediaInfo = uploadService.uploadImages(imageFiles, path);

    // then
    Assertions.assertThat(mediaInfo).isNotNull();
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
    List<MediaInfo> mediaInfo = uploadService.uploadImages(imageFiles, path);

    // then
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImage_Success() throws Exception{
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile imageFile = createMockImageFile("test" + nextId++);

    // when
    MediaInfo mediaInfo = uploadService.uploadImage(imageFile, path);

    // then
    Assertions.assertThat(mediaInfo).isNotNull();
  }

  @Test(expected = FileIsNotImageException.class)
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void uploadImage_Fail_FileIsNotImageException() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);

    // when
    MediaInfo mediaInfo = uploadService.uploadImage(textFile, path);

    // then
  }

  @Test
  @WithMockUser(username = TEST_USER_CLASS_OF, roles = "USER")
  public void deleteFile_Success() {
    // given
    String path = "/" + TEST_USER_CLASS_OF;
    MockMultipartFile textFile = createMockTextFile("test" + nextId++);
    MediaInfo mediaInfo = uploadService.uploadFile(textFile, path);

    // when, then
    uploadService.deleteFile(mediaInfo.getModifyName(), "/" + TEST_USER_CLASS_OF );
  }

  private MockMultipartFile createMockTextFile(String fileName) {
    return new MockMultipartFile(
        "file",
         fileName + ".txt" ,
        "text/plain" ,
        "hello file".getBytes()
    );
  }

  private MockMultipartFile createMockImageFile(String fileName) throws Exception {
    FileInputStream fileInputStream = new FileInputStream("src/test/resources/images/logo.jpg");
    return new MockMultipartFile(
      "file",
      fileName + ".jpg",
      null,
      fileInputStream
    );
  }
}

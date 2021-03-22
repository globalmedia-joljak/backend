package kr.joljak.api.upload.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class ImagesUploadRequest {
  List<MultipartFile> images;
  UploadRequest uploadRequest;
}

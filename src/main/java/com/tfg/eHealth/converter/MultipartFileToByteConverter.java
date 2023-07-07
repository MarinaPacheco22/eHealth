package com.tfg.eHealth.converter;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MultipartFileToByteConverter {

    public List<MultipartFile> convert(List<byte[]> byteList) throws IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>();

        for (byte[] bytes : byteList) {
            MockMultipartFile multipartFile = new MockMultipartFile(
                    "file",
                    "filename",
                    null,
                    bytes
            );
            multipartFiles.add(multipartFile);
        }

        return multipartFiles;
    }
}

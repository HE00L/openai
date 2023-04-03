package com.xoto.openai.imageModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

@RestController
@RequestMapping("/generateImage")
public class ImageResource {
    private OpenAiService service = new OpenAiService(System.getenv("API_KEY"));

    @GetMapping("/get")
    public ResponseEntity<byte[]> getImage(@RequestParam final String desc) throws IOException {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(desc)
                .build();

        URL imageUrl = new URL(service.createImage(request).getData().get(0).getUrl());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (InputStream inputStream = imageUrl.openStream()) {
            byte[] buffer = new byte[4096];
            int length = -1;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        }

        byte[] imageData = outputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}

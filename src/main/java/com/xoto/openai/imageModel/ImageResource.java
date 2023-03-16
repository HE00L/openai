package com.xoto.openai.imageModel;

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
    public String getImage(@RequestParam final String desc) {
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(desc)
                .build();

        return (service.createImage(request).getData().get(0).getUrl());
    }
}

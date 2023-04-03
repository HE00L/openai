package com.xoto.openai.adaModel;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

@RestController
@RequestMapping("/ada")
public class AdaResource {

    private OpenAiService service = new OpenAiService(System.getenv("API_KEY"));

    @PostMapping("/get")
    public CompletionChoice getAnswer(@RequestBody Map<String, Object> map) {

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt(map.getOrDefault("content", "").toString())
                .echo(true)
                .maxTokens(Integer.parseInt(map.getOrDefault("length", "100").toString()))
                .user(map.getOrDefault("user", "human").toString())
                .n(1)
                .build();

        return service.createCompletion(completionRequest).getChoices().get(0);
    }

}

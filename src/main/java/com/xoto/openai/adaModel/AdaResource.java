package com.xoto.openai.adaModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

@RestController
@RequestMapping("/ada")
public class AdaResource {

    private OpenAiService service = new OpenAiService(System.getenv("API_KEY"));

    @GetMapping("/get")
    public CompletionChoice getAnswer(@RequestParam final String content,
                          @RequestParam String user,
                          @RequestParam(defaultValue = "100") int length) {
        if (user == null) {
            user = "human";
        }
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt(content)
                .echo(true)
                .maxTokens(length)
                .user(user)
                .n(1)
                .build();

        return service.createCompletion(completionRequest).getChoices().get(0);
    }

}

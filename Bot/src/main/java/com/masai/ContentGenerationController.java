package com.masai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ContentGenerationController {

    @Autowired
    private OpenAIHelper openaiHelper;

    @PostMapping("/generate-text")
    public String generateText(@RequestBody TextGenerationRequest request) {
        String generatedText = openaiHelper.generateText(request.getTopic());
        return generatedText;
    }

    @PostMapping("/summarize-text")
    public String summarizeText(@RequestBody TextSummarizationRequest request) {
        String summarizedText = openaiHelper.summarizeText(request.getText());
        return summarizedText;
    }

    @PostMapping("/translate-text")
    public String translateText(@RequestBody LanguageTranslationRequest request) {
        String translatedText = openaiHelper.translateText(request.getText(), request.getSourceLanguage(), request.getTargetLanguage());
        return translatedText;
    }
}


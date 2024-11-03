//package com.gadv.screenmatch.service;
//
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.service.OpenAiService;
//
//public class ConsultChatGPT {
//    public static String getTranslation(String texto) {
//        OpenAiService service = new OpenAiService("TU-API-KEY");
//
//        CompletionRequest requisition = CompletionRequest.builder()
//                .model("gpt-3.5-turbo-instruct")
//                .prompt("traduce a español el siguiente texto: " + texto)
//                .maxTokens(1000)
//                .temperature(0.7)
//                .build();
//
//        var response = service.createCompletion(requisition);
//        return response.getChoices().get(0).getText();
//    }
//}

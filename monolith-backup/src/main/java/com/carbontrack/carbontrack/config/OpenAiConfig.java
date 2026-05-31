package com.carbontrack.carbontrack.config;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAiConfig {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Bean
    public OpenAIClient openAIClient() {
        // It reads the API key from the environment variable OPENAI_API_KEY
//        return OpenAIOkHttpClient.fromEnv();
        return OpenAIOkHttpClient.builder()
                .apiKey(openAiApiKey)
                .build();
    }
/*

    @Getter
    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Value("${spring.ai.openai.model}")
    private String modelName;

    */
/*@Bean
    public OpenAiClient openAiClient() {
        return new OpenAiClient(openAiApiKey, modelName);
    }*//*


    @Bean
    public OpenAiApi openAiApi() {
        return OpenAiApi.builder()
                .apiKey(openAiApiKey)
                .build();
    }

    @Bean
    public RestTemplate template(){
        RestTemplate restTemplate=new RestTemplate();

        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().setBearerAuth(openAiApiKey);      // Authorization: Bearer <API_KEY>
            request.getHeaders().set("Content-Type", "application/json"); // Always JSON
            return execution.execute(request, body);
        });

        restTemplate.getInterceptors().add((request, body, execution) ->{
            request.getHeaders().add("Authorization", "Bearer " + openAiApiKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
*/

}

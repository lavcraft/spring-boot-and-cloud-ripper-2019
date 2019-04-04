package com.naya.exams.frontendstarter.integration.wrapper;

import com.naya.exams.frontendstarter.aop.FrontendRestController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringJUnitConfig
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT) // Try use @WebMvcTest. Shoot yourself in the foot. Because mock mvc return real object i guess..
public class StarterTest {
    @Autowired MockMvc mvc;

    @Test
    @DisplayName("Wrap controller response to result root for @FrontendRestController")
    public void should_wrap_frontend_controller_response() throws Exception {
        mvc.perform(get("/test-frontend"))
                .andExpect(jsonPath("result.data", equalTo("test")));
    }

    @Test
    @DisplayName("Save default behaviour without wrapping for @RestController")
    public void should_not_wrap_rest_controller_response() throws Exception {
        mvc.perform(get("/test"))
                .andExpect(jsonPath("data", equalTo("test")));
    }

    @TestConfiguration
    public static class ExampleConfiguration {

        @RestController
        public static class DefaultController {
            @RequestMapping("/test")
            public ExampleResponse test() {
                return new ExampleResponse("test");
            }
        }

        @FrontendRestController
        public static class FrontendController {
            @RequestMapping("/test-frontend")
            public ExampleResponse test() {
                return new ExampleResponse("test");
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ExampleResponse {
            private String data;
        }
    }
}

package fr.kevin.llps.subscriber.bc.integration;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.repository.SubscriberRepository;
import fr.kevin.llps.subscriber.bc.utils.PostgreSQLContainerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static fr.kevin.llps.subscriber.bc.utils.TestUtils.readResource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
class SubscriberIntegrationTest extends PostgreSQLContainerTest {

    @Value("classpath:/json/subscriber-request.json")
    private Resource subscriberRequest;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @AfterEach
    void tearDown() {
        subscriberRepository.deleteAll();
    }

    @Test
    void shouldCreate() throws Exception {
        mockMvc.perform(post("/subscribers")
                        .content(readResource(subscriberRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.firstname", is("jean")))
                .andExpect(jsonPath("$.lastname", is("dupont")))
                .andExpect(jsonPath("$.email", is("jean.dupont@gmail.com")))
                .andExpect(jsonPath("$.phone", is("0654323456")))
                .andExpect(jsonPath("$.enabled", is(true)));

        List<SubscriberEntity> subscriberEntities = subscriberRepository.findAll();

        assertThat(subscriberEntities).isNotNull()
                .hasSize(1)
                .extracting("firstname", "lastname", "email", "phone", "enabled")
                .containsExactly(
                        tuple("jean",
                                "dupont",
                                "jean.dupont@gmail.com",
                                "0654323456",
                                true));
    }

}
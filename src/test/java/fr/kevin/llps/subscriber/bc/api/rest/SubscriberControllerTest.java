package fr.kevin.llps.subscriber.bc.api.rest;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberDtoMapper;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberEntityMapper;
import fr.kevin.llps.subscriber.bc.service.SubscriberService;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.oneSubscriberRequestDto;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberDtoSample.oneSubscriberResponseDto;
import static fr.kevin.llps.subscriber.bc.sample.SubscriberEntitySample.oneSubscriberEntity;
import static fr.kevin.llps.subscriber.bc.utils.TestUtils.readResource;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubscriberController.class)
class SubscriberControllerTest {

    @Value("classpath:/json/subscriber-request.json")
    private Resource subscriberRequest;

    @Value("classpath:/json/subscriber-response.json")
    private Resource subscriberResponse;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberService subscriberService;

    @MockBean
    private SubscriberEntityMapper subscriberEntityMapper;

    @MockBean
    private SubscriberDtoMapper subscriberDtoMapper;

    @Test
    void shouldCreate() throws Exception {
        SubscriberRequestDto subscriberRequestDto = oneSubscriberRequestDto();
        SubscriberResponseDto subscriberResponseDto = oneSubscriberResponseDto();
        SubscriberEntity subscriberEntity = oneSubscriberEntity();
        SubscriberEntity savedSubscriber = oneSubscriberEntity();
        savedSubscriber.setId(UUID.fromString("dde8bfa2-4922-11ec-81d3-0242ac130003"));

        when(subscriberEntityMapper.map(subscriberRequestDto)).thenReturn(subscriberEntity);
        when(subscriberService.save(subscriberEntity)).thenReturn(savedSubscriber);
        when(subscriberDtoMapper.map(savedSubscriber)).thenReturn(subscriberResponseDto);

        mockMvc.perform(post("/subscribers")
                        .content(readResource(subscriberRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(readResource(subscriberResponse), true));

        verify(subscriberEntityMapper).map(subscriberRequestDto);
        verify(subscriberService).save(subscriberEntity);
        verify(subscriberDtoMapper).map(savedSubscriber);
        verifyNoMoreInteractions(subscriberEntityMapper, subscriberService, subscriberDtoMapper);
    }

}

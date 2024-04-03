package fr.kevin.llps.subscriber.bc.api.rest;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberDtoMapper;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberEntityMapper;
import fr.kevin.llps.subscriber.bc.service.SubscriberService;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribers")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final SubscriberEntityMapper subscriberEntityMapper;
    private final SubscriberDtoMapper subscriberDtoMapper;

    @PostMapping
    public SubscriberResponseDto create(@RequestBody SubscriberRequestDto subscriberRequestDto) {
        SubscriberEntity subscriberEntity = subscriberEntityMapper.map(subscriberRequestDto);

        SubscriberEntity savedSubscriber = subscriberService.save(subscriberEntity);

        return subscriberDtoMapper.map(savedSubscriber);
    }

}

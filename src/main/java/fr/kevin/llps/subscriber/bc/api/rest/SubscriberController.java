package fr.kevin.llps.subscriber.bc.api.rest;

import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberPatchRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberRequestDto;
import fr.kevin.llps.subscriber.bc.api.rest.dto.SubscriberResponseDto;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberDtoMapper;
import fr.kevin.llps.subscriber.bc.api.rest.mapper.SubscriberEntityMapper;
import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import fr.kevin.llps.subscriber.bc.exception.SubscriberAlreadyExistsException;
import fr.kevin.llps.subscriber.bc.exception.SubscriberEntityNotFoundException;
import fr.kevin.llps.subscriber.bc.service.SubscriberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static fr.kevin.llps.subscriber.bc.utils.Constants.SUBSCRIBER_NOT_FOUND_ERROR_MESSAGE;

@RestController
@RequestMapping("/subscribers")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;
    private final SubscriberEntityMapper subscriberEntityMapper;
    private final SubscriberDtoMapper subscriberDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriberResponseDto create(@Valid @RequestBody SubscriberRequestDto subscriberRequestDto) {
        SubscriberEntity subscriberEntity = subscriberEntityMapper.map(subscriberRequestDto);

        if (subscriberService.subscriberExists(subscriberEntity)) {
            String errorMessage = String.format("Subscriber already exist (email : %s, phone : %s)", subscriberEntity.getEmail(), subscriberEntity.getPhone());
            throw new SubscriberAlreadyExistsException(errorMessage);
        }

        SubscriberEntity savedSubscriber = subscriberService.save(subscriberEntity);

        return subscriberDtoMapper.map(savedSubscriber);
    }

    @GetMapping("/{id}")
    public SubscriberResponseDto getById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        SubscriberEntity subscriberEntity = subscriberService.getSubscriberEntity(uuid)
                .orElseThrow(() -> new SubscriberEntityNotFoundException(SUBSCRIBER_NOT_FOUND_ERROR_MESSAGE));

        return subscriberDtoMapper.map(subscriberEntity);
    }

    @PostMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disable(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);

        SubscriberEntity subscriberEntity = subscriberService.getSubscriberEntity(uuid)
                .orElseThrow(() -> new SubscriberEntityNotFoundException(SUBSCRIBER_NOT_FOUND_ERROR_MESSAGE));

        subscriberService.disable(subscriberEntity);
    }

    @PatchMapping("/{id}")
    public SubscriberResponseDto patch(@PathVariable String id, @Valid @RequestBody SubscriberPatchRequestDto subscriberPatchRequestDto) {
        UUID uuid = UUID.fromString(id);
        SubscriberEntity subscriberEntity = subscriberEntityMapper.map(subscriberPatchRequestDto);

        SubscriberEntity updatedSubscriber = subscriberService.patch(uuid, subscriberEntity);

        return subscriberDtoMapper.map(updatedSubscriber);
    }

}

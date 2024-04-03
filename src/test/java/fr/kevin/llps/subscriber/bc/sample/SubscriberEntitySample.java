package fr.kevin.llps.subscriber.bc.sample;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SubscriberEntitySample {

    public static SubscriberEntity oneSubscriberEntity() {
        return new SubscriberEntity(
                "jean",
                "dupont",
                "jean.dupont@gmail.com",
                "0654323456",
                true);
    }

}

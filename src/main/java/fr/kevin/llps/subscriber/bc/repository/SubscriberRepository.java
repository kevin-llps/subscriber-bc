package fr.kevin.llps.subscriber.bc.repository;

import fr.kevin.llps.subscriber.bc.domain.SubscriberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, UUID> {
}

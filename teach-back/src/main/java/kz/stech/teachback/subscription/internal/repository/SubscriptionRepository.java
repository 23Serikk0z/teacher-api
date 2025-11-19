package kz.stech.teachback.subscription.internal.repository;

import kz.stech.teachback.subscription.internal.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {
}
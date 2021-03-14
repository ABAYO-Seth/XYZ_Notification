package rw.gov.irembo.notification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.gov.irembo.notification.domain.Subsciber;

@Repository
public interface AppRepository extends JpaRepository<Subsciber,Integer> {

    Subsciber findBySubscriberKey(String key);
}

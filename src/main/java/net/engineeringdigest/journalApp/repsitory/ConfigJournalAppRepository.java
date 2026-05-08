package net.engineeringdigest.journalApp.repsitory;

import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, Object> {
}

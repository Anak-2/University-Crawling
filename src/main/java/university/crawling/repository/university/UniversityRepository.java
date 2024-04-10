package university.crawling.repository.university;

import university.crawling.entity.UniversityJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository {

    void put(final UniversityJpaEntity universityJpaEntity);

    UniversityJpaEntity getByName(final String name);

    UniversityJpaEntity findByName(final String name);

    List<UniversityJpaEntity> findAll();
}

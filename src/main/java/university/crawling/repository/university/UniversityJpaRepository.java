package university.crawling.repository.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import university.crawling.entity.UniversityJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UniversityJpaRepository extends JpaRepository<UniversityJpaEntity, Long> {

    Optional<UniversityJpaEntity> findByName(final String name);

    List<UniversityJpaEntity> findAll();
}

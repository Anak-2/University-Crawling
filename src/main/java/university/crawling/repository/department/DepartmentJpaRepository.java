package university.crawling.repository.department;

import org.springframework.data.jpa.repository.JpaRepository;
import university.crawling.entity.DepartmentJpaEntity;

import java.util.List;

public interface DepartmentJpaRepository extends JpaRepository<DepartmentJpaEntity, Long> {

    List<DepartmentJpaEntity> findAllByUniversityId(final long universityId);
}

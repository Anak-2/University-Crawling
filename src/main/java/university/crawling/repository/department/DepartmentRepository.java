package university.crawling.repository.department;

import university.crawling.entity.DepartmentJpaEntity;
import university.crawling.entity.UniversityJpaEntity;

import java.util.List;

public interface DepartmentRepository {

    void save(final DepartmentJpaEntity departmentJpaEntity);

    List<String> findAllDepartmentOfUniversity(final long universityId);
}

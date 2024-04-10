package university.crawling.repository.department;

import org.springframework.stereotype.Repository;
import university.crawling.entity.DepartmentJpaEntity;
import university.crawling.entity.UniversityJpaEntity;
import university.crawling.repository.university.UniversityJpaRepository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository{

    private final DepartmentJpaRepository departmentJpaRepository;

    public DepartmentRepositoryImpl(final DepartmentJpaRepository departmentJpaRepository) {
        this.departmentJpaRepository = departmentJpaRepository;
    }

    @Override
    public void save(final DepartmentJpaEntity departmentJpaEntity) {
        departmentJpaRepository.save(departmentJpaEntity);
    }

    @Override
    public List<String> findAllDepartmentOfUniversity(final long universityId) {
        return departmentJpaRepository.findAllByUniversityId(universityId).stream()
                .map((DepartmentJpaEntity departmentJpaEntity) -> {
                    return departmentJpaEntity.getMajorName();
                })
                .toList();
    }
}

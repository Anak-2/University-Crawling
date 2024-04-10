package university.crawling.repository.university;

import org.springframework.stereotype.Repository;
import university.crawling.entity.UniversityJpaEntity;
import university.crawling.global.error.NotFoundException;

import java.util.List;

import static university.crawling.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_NOT_FOUND;

@Repository
public class UniversityRepositoryImpl implements UniversityRepository{

    private final UniversityJpaRepository universityJpaRepository;

    public UniversityRepositoryImpl(final UniversityJpaRepository universityJpaRepository) {
        this.universityJpaRepository = universityJpaRepository;
    }

    @Override
    public void put(final UniversityJpaEntity universityJpaEntity) {
        UniversityJpaEntity findUniversity = findByName(universityJpaEntity.getName());

        if(findUniversity == null){
            universityJpaRepository.save(universityJpaEntity);
        }else if(!universityJpaEntity.getUniversityEmail().isEmpty()){
            findUniversity.updateEmail(universityJpaEntity.getUniversityEmail());
            universityJpaRepository.save(findUniversity);
        }
    }

    @Override
    public UniversityJpaEntity getByName(final String name) {
        return universityJpaRepository.findByName(name).orElseThrow(() -> new NotFoundException(name+UNIVERSITY_NOT_FOUND.message()));
    }

    @Override
    public UniversityJpaEntity findByName(final String name){
        return universityJpaRepository.findByName(name).orElse(null);
    }

    @Override
    public List<UniversityJpaEntity> findAll() {
        return universityJpaRepository.findAll();
    }
}

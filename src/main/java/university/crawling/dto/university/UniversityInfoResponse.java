package university.crawling.dto.university;

import lombok.Builder;
import university.crawling.entity.UniversityJpaEntity;

@Builder
public record UniversityInfoResponse (
    long universityId,
    String name,
    String universityEmail
){

    public static UniversityInfoResponse of(UniversityJpaEntity universityJpaEntity){
        return UniversityInfoResponse.builder()
                .universityId(universityJpaEntity.getId())
                .name(universityJpaEntity.getName())
                .universityEmail(universityJpaEntity.getUniversityEmail())
                .build();
    }
}

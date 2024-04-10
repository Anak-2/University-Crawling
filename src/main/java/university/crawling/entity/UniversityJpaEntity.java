package university.crawling.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "university")
@Entity
public class UniversityJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university")
    private long id;

    private String name;

    private String universityEmail;

    public void updateEmail(String universityEmail){
        this.universityEmail = universityEmail;
    }

    protected UniversityJpaEntity() {
    }

    @Builder
    public UniversityJpaEntity(final String name, final String universityEmail) {
        this.name = name;
        this.universityEmail = universityEmail;
    }
}

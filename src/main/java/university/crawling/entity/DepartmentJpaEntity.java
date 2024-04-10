package university.crawling.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "department")
@Entity
public class DepartmentJpaEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department")
    private long id;

    private long universityId;

    private String majorName;

    protected DepartmentJpaEntity(){
    }

    @Builder
    public DepartmentJpaEntity(final long universityId, final String majorName) {
        this.universityId = universityId;
        this.majorName = majorName;
    }
}

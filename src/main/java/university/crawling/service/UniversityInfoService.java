package university.crawling.service;

import university.crawling.dto.university.UniversityInfoResponse;

import java.util.List;

public interface UniversityInfoService {

    void putAllUniversity();

    void putAllDepartment();

    List<UniversityInfoResponse> getAllUniversity();

    List<String> getAllDepartment(long universityId, String universityName);
}

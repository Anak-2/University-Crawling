package university.crawling.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import university.crawling.dto.university.UniversityInfoResponse;
import university.crawling.entity.DepartmentJpaEntity;
import university.crawling.entity.UniversityJpaEntity;
import university.crawling.global.error.BadRequestException;
import university.crawling.global.error.InternalServerException;
import university.crawling.repository.department.DepartmentRepository;
import university.crawling.repository.university.UniversityRepository;

import java.util.ArrayList;
import java.util.List;

import static university.crawling.global.constant.exception.CrawlingExceptionConstant.CRAWLING_PARSE_ERROR;
import static university.crawling.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT;

@Service
public class UniversityInfoServiceImpl implements UniversityInfoService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UniversityRepository universityRepository;
    private final DepartmentRepository departmentRepository;

    @Value("${university.api.key}")
    private String apiKey;

    public UniversityInfoServiceImpl(final RestTemplate restTemplate, final ObjectMapper objectMapper,
                                     final UniversityRepository universityRepository, final DepartmentRepository departmentRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.universityRepository = universityRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void putAllUniversity() {
        fetchAllUniversity();
    }

    @Override
    public void putAllDepartment() {
        List<String> allMajor = fetchAllMajor();
        for(String eachMajor : allMajor){
            fetchUniversitiesByMajor(eachMajor);
        }
    }

    @Override
    public List<UniversityInfoResponse> getAllUniversity() {
        List<UniversityJpaEntity> universityJpaEntities = universityRepository.findAll();
        return universityJpaEntities.stream()
                .map(UniversityInfoResponse::of)
                .toList();
    }

    @Override
    public List<String> getAllDepartment(final long universityId, final String universityName) {
        if(universityId != -1){
            return getAllDepartmentByUniversityId(universityId);
        }
        if(!universityName.isEmpty()){
            return getAllDepartmentByUniversityName(universityName);
        }
        throw new BadRequestException(UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT.message());
    }

    public List<String> getAllDepartmentByUniversityId(final long universityId) {
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }

    public List<String> getAllDepartmentByUniversityName(final String universityName) {
        long universityId = universityRepository.getByName(universityName).getId();
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }

    public void fetchAllUniversity(){
        String url = "http://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="+apiKey+"&contentType=json&svcType=api&svcCode=SCHOOL&gubun=univ_list&perPage=475";
        String response = restTemplate.getForObject(url, String.class);

        try{
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path("dataSearch").path("content");
            List<String> collegeType = List.of("전문대학","대학교");
            for (JsonNode item : content) {
                String link = item.get("link").asText();
                String schoolName = item.get("schoolName").asText();
                String schoolType = item.get("schoolType").asText();
                if(!collegeType.contains(schoolType)){
                    continue;
                }

                // 'http://' 또는 'https://' 부분 제거
                int slashCnt = 0;
                while(slashCnt < 2){
                    int index = link.indexOf('/');
                    if(index == -1){
                        break;
                    }
                    link = link.substring(index+1);
                    slashCnt++;
                }
                // 앞에 www. 제거
                int wwwIndex = link.indexOf("www.");
                if(wwwIndex != -1){
                    link = link.substring(wwwIndex+"www.".length());
                }

                // ac.kr 뒷부분 제거
                int ackrIndex = link.indexOf("ac.kr");
                if(ackrIndex != -1){
                    link = link.substring(0, ackrIndex + "ac.kr".length());
                }

                UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                        .universityEmail(link)
                        .name(schoolName)
                        .build();
                universityRepository.put(universityJpaEntity);
            }
        } catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    public void fetchUniversitiesByMajor(final String majorSeq){
        String url = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="+apiKey+"&svcType=api&svcCode=MAJOR_VIEW&contentType=json&gubun=univ_list&univSe=대학&perPage=1000&majorSeq="+majorSeq;

        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path("dataSearch").path("content");

            for (JsonNode item : content) {
                JsonNode universities = item.path("university");
                for (JsonNode university : universities) {
                    String majorName = university.path("majorName").asText();
                    String schoolName = university.path("schoolName").asText();

                    UniversityJpaEntity universityJpaEntity = universityRepository.findByName(schoolName);
                    if(universityJpaEntity != null){
                        DepartmentJpaEntity saveDepartment = DepartmentJpaEntity.builder()
                                .universityId(universityJpaEntity.getId())
                                .majorName(majorName)
                                .build();
                        departmentRepository.save(saveDepartment);
                    }
                }
            }
        }catch (JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }

    public List<String> fetchAllMajor(){
        String url = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey="+apiKey+"&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&univSe=대학&perPage=1000";
        String response = restTemplate.getForObject(url, String.class);

        List<String> allMajor = new ArrayList<>();
        try{
            JsonNode root = objectMapper.readTree(response);
            JsonNode content = root.path("dataSearch").path("content");

            for(JsonNode item : content){
                String majorSeq = item.get("majorSeq").asText();
                allMajor.add(majorSeq);
                System.out.println(majorSeq);
            }

            return allMajor;
        }catch(JsonProcessingException e){
            throw new InternalServerException(CRAWLING_PARSE_ERROR.message());
        }
    }
}

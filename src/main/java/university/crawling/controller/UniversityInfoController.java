package university.crawling.controller;

import org.springframework.web.bind.annotation.*;
import university.crawling.dto.university.UniversityInfoResponse;
import university.crawling.service.UniversityInfoService;

import java.util.List;

@RequestMapping("/api/universities")
@RestController
public class UniversityInfoController {

    private final UniversityInfoService universityInfoService;

    public UniversityInfoController(final UniversityInfoService universityInfoService) {
        this.universityInfoService = universityInfoService;
    }

    @PostMapping("/")
    public void initializeUniversity(){
        universityInfoService.putAllUniversity();
        universityInfoService.putAllDepartment();
    }

    @PostMapping("/university")
    public void updateCollegeInfo(){
        universityInfoService.putAllUniversity();
    }

    @GetMapping("/university")
    public List<UniversityInfoResponse> getCollegeInfo(){
        return universityInfoService.getAllUniversity();
    }


    @PostMapping("/department")
    public void updateDepartmentInfo(){
        universityInfoService.putAllDepartment();
    }

    @GetMapping("/department")
    public List<String> getDepartmentInfoById(@RequestParam(defaultValue = "-1", required = false) long universityId,
                                              @RequestParam(defaultValue = "", required = false) String universityName){
        return universityInfoService.getAllDepartment(universityId, universityName);
    }
}

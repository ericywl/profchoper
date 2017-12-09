package profchoper.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessorRestController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/api/professors")
    public List<Professor> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/api/professors/{alias}")
    public Professor getProfessorByAlias(@PathVariable String alias) {
        return professorService.getProfessorByAlias(alias);
    }

    @GetMapping(value = "/api/professors", params = "name")
    public Professor getProfessorByName(@RequestParam String name) {
        return professorService.getProfessorByName(name);
    }

    @GetMapping(value = "/api/professors", params = "course")
    public List<Professor> getProfessorsByCourse(@RequestParam String course) {
        return professorService.getProfessorsByCourseId(course);
    }
}

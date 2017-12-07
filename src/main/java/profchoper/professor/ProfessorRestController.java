package profchoper.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessorRestController {

    @Autowired
    private ProfessorService professorService;

    @GetMapping("/api/professors")
    public List<Professor> findAll() {
        return professorService.getAllProfessors();
    }
}

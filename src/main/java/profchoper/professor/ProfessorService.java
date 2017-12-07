package profchoper.professor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public List<Professor> getProfessorsByCourseId(String courseId) {
        return professorRepository.findByCourseId(courseId);
    }

    public Professor getProfessorByAlias(String alias) {
        return professorRepository.findByAlias(alias);
    }
}

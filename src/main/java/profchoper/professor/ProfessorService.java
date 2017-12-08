package profchoper.professor;

import java.util.List;

public interface ProfessorService {
    List<Professor> getAllProfessors();

    List<Professor> getProfessorsByCourseId(int courseId);

    Professor getProfessorByAlias(String alias);
}

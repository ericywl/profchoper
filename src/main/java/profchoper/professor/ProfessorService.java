package profchoper.professor;

import java.util.List;

public interface ProfessorService {
    List<Professor> getAllProfessors();

    List<Professor> getProfessorsByCourseId(String courseId);

    Professor getProfessorByAlias(String alias);

    Professor getProfessorByName(String name);

    Professor getProfessorByEmail(String email);
}

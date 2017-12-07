package profchoper.TestObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    private TestRepository testRepo;

    @Autowired
    public TestService(TestRepository testRepo) {
        this.testRepo = testRepo;
    }

    public List<Test> getAllTests() {
        List<Test> output = new ArrayList<>();
        testRepo.findAll().forEach(output :: add);

        return output;
    }

    public Test getTestById(int id) {
        return testRepo.findOne(id);
    }

    public void addTest(Test test) {
        testRepo.save(test);
    }

    public void updateTest(Test test) {
        testRepo.save(test);
    }

    public void deleteTest(int id) {
        testRepo.delete(id);
    }
}

package profchoper.TestObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRestController {

    private final TestService testService;

    @Autowired
    public TestRestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/api/tests")
    public List<Test> findAll() {
        return testService.getAllTests();
    }

    @GetMapping("/api/tests/{id}")
    public Test findById(@PathVariable int id) {
        return testService.getTestById(id);
    }
}

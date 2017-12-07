package profchoper.TestObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value="/api/tests")
    public void addtest(@RequestBody Test test) {
        testService.addTest(test);
    }

    @PutMapping(value="/api/tests")
    public void updatetest(@RequestBody Test test) {
        testService.updateTest(test);
    }

    @DeleteMapping(value="/api/tests/{id}")
    public void deletetest(@PathVariable int id) {
        testService.deleteTest(id);
    }
}

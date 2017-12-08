package profchoper.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String customerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String customerSubmit(@ModelAttribute Customer customer, Model model) {

        model.addAttribute("customer", customer);
        String info = String.format("Customer Submission: id = %d, firstname = %s, lastname = %s",
                customer.getId(), customer.getFirstname(), customer.getLastname());
        log.info(info);
        customerRepository.save(customer);

        return "result";
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public String customerSubmit(@RequestParam("id") long id, Model model) {

        Customer customer = customerRepository.findOne(id);
        model.addAttribute("customer", customer);

        return "load";
    }

}

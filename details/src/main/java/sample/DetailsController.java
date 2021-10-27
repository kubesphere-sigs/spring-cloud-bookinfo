package sample;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DetailsController {

    @Autowired
    DetailService service;

    @RequestMapping("/products/{id}")
    public Optional<Details> getDetail(@PathVariable final int id) {
        return service.getDetailById(id);
    }
}
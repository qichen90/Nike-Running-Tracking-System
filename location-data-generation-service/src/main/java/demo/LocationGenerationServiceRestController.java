package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationGenerationServiceRestController {
    private LocationRepository repository;

    @Autowired
    public LocationGenerationServiceRestController(LocationRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/bulk/locations", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Iterable<Location> upload(@RequestBody List<Location> locations) {
        return this.repository.save(locations);
    }
}

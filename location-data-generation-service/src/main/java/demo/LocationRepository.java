package demo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "locations")
public interface LocationRepository extends PagingAndSortingRepository<Location, String> {

}

package demo.service.Impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import demo.model.CurrentPosition;
import demo.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class DefaultPositionService implements PositionService {
    //logging before REST API

//    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private RestTemplate restTemplate;


//    @Value("${com.qi.running.location.distribution}")
//    private String runningLocationDistribution;

    @HystrixCommand(fallbackMethod = "processPositionInfoFallback")
    @Override
    public void processPositionInfo(long id, CurrentPosition currentPosition, boolean sentPositionsToDistributionService) {
        String runningLocationDistribution = "http://running-location-distribution";
        if(sentPositionsToDistributionService){
            log.info(String.format("Thread %d Simulator is calling distribution REST API", Thread.currentThread().getId()));
            this.restTemplate.postForLocation(runningLocationDistribution + "api/locations", currentPosition);
        }
    }

    public void processPositionInfoFallback(long id, CurrentPosition currentPosition, boolean sentPositionsToDistributionService){
        log.error("Hystrix Fallback Method. Unable to send message for distribution");
    }
}

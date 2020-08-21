package cabtrip.server;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.LinkedHashMap;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CabTripAppTest {

    @Autowired
    private TestRestTemplate restTemplate;
    Logger logger = LoggerFactory.getLogger(CabTripAppTest.class);
    
    //Test a correct request
    @Test
    public void shouldGetCabTripData() {
        ResponseEntity<List> response = restTemplate.getForEntity("/cabtripcountpickup?pickupdate=2013-12-01&cache=false",List.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


   //Test a request which has incorrect value on pickupdate
    @Test
    public void shouldGetDateFormatException() {
   
        ResponseEntity<Object> response = restTemplate.getForEntity("/cabtripcountpickup?pickupdate=1&cache=false",Object.class);
        LinkedHashMap error = (LinkedHashMap)response.getBody();
        
       assertEquals("The date formate is should be yyyy-mm-dd", error.get("message"));
       
    }
    
  //Test a request which has missing parameters
    @Test
    public void getParameterMissingException() {
   
        ResponseEntity<Object> response = restTemplate.getForEntity("/cabtripcountpickup",Object.class);
        LinkedHashMap error = (LinkedHashMap)response.getBody();
        logger.info(error+"");
        assertEquals("Required String parameter 'pickupdate' is not present", error.get("message"));
       
    }
}

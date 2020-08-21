
package cabtrip.server.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cabtrip.server.service.CabTripDataService;
import cabtrip.server.utils.CommonUtils;
import cabtrip.server.model.CabTripCount;
@RestController
public class CabTripDataController {
	
	@Autowired
	CabTripDataService cabTripDataService;
	
	
	@RequestMapping("/")
	String index() {
		return "<h1>Welcome to New york</h1>";
	}
	
	//rest api entry for retrieving cab trip summary
	@RequestMapping("/cabtripcountpickup")
	@Validated
	List<CabTripCount> findCabTripCountByPickupDate(@RequestParam String pickupdate,@RequestParam(defaultValue = "true") Boolean cache) throws Exception {
		
		List<CabTripCount> cabTripCount=new ArrayList<CabTripCount>();
		if(pickupdate!=null&&CommonUtils.isValidFormat("yyyy-mm-dd", pickupdate))
			 cabTripCount= cabTripDataService.findCabTripCountByPickupDate(pickupdate,cache);
		else
			throw new Exception("The date formate is should be yyyy-mm-dd");
		
		
        return cabTripCount;
    }
	
	//rest api entry for cleaning  the cache data of a specific date
	@RequestMapping("/cleancache/{pickupdate}")
	String cleanCacheOnDate(@PathVariable String pickupdate) throws Exception {
		if(pickupdate!=null&&CommonUtils.isValidFormat("yyyy-mm-dd", pickupdate)) {
		cabTripDataService.cleanCacheOnDate(pickupdate);
		return "Cache of "+pickupdate+" has been cleaned";
		}
		else
			throw new Exception("The date formate is should be yyyy-mm-dd");
		
	}
	
	//rest api entry for cleaning whole cache
	@RequestMapping("/cleancache")
	String cleanCache() {
		
		cabTripDataService.cleanCache();
		return "Whole cache has been cleaned";
	}
	

}

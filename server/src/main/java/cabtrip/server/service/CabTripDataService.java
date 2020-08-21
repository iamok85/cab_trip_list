/**
 * 
 */
package cabtrip.server.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cabtrip.server.model.CabTripCount;
import cabtrip.server.utils.CabTripCountRowMapper;

@Service
public class CabTripDataService {

	Logger logger = LoggerFactory.getLogger(CabTripDataService.class);

	// Cache for storing sql query result
	private static Map<String, List<CabTripCount>> cacheData = new ConcurrentHashMap<String, List<CabTripCount>>();
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(readOnly = true)
	public List<CabTripCount> findCabTripCountByPickupDate(String pickupDate, Boolean cache) {

		List<CabTripCount> cabTripCountList = null;

		// if the cache flag is on then retrieve data from the HashMap
		if (cache) {
			cabTripCountList = cacheData.get(pickupDate);
		}
		// if the list is not empty
		if (cabTripCountList != null && cabTripCountList.size() > 0) {
			logger.info("The result is from cache");
			return cabTripCountList;
		}

		// if the list is empty
		cabTripCountList = jdbcTemplate.query(
				"select medallion, count(medallion) as trip_count from cab_trip_data where DATE(pickup_datetime)='"
						+ pickupDate + "' group by medallion order by trip_count desc",
				new CabTripCountRowMapper());

		// store the result into cache
		cacheData.put(pickupDate, cabTripCountList);
		logger.info("The result is from database");
		return cabTripCountList;
	}

	// clean up whole cache
	public void cleanCache() {

		cacheData.clear();
		logger.info("Cache has been cleaned up");
	}

	// clean up cache on a specific date
	public void cleanCacheOnDate(String date) {

		cacheData.remove(date);
		logger.info("Cache of date " + date + " has been cleaned up");
	}
}

package cabtrip.server.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import cabtrip.server.model.CabTripCount;

//This class is for creating cab trip count instance from sql result set
public class CabTripCountRowMapper implements RowMapper<CabTripCount>{

	 @Override
    public CabTripCount mapRow(ResultSet rs, int rowNum) throws SQLException {
		 CabTripCount cabTripCount = new CabTripCount();
		 cabTripCount.setMedallion(rs.getString("medallion"));
		 cabTripCount.setCount(rs.getInt("trip_count"));
        return cabTripCount;
    }

}

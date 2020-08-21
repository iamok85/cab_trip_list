package cabtrip.server.model;


//This model class for holding trip data
public class CabTripCount {

	private String medallion;
	private Integer count;
	
	public String getMedallion() {
		return medallion;
	}
	public void setMedallion(String medallion) {
		this.medallion = medallion;
	}
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}

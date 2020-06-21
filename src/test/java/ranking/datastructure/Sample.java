package ranking.datastructure;

import java.util.List;

public class Sample {

	private List<DataPoint> dataPoints;

	public Sample(List<DataPoint> dataPoints) {
		this.dataPoints = dataPoints;
	}

	public List<DataPoint> getDataPoints() {
		return this.dataPoints;
	}

	public int length() {
		return this.dataPoints.size();
	}
}

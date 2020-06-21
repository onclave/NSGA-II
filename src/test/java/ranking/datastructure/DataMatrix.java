package ranking.datastructure;

import java.util.List;

public class DataMatrix {

	private List<Sample> samples;

	public DataMatrix(List<Sample> samples) {
		this.samples = samples;
	}

	public List<Sample> getSamples() {
		return this.samples;
	}

	public Sample getSample(int index) {
		return this.samples.get(index);
	}

	public int sampleCount() {
		return this.samples.size();
	}
}

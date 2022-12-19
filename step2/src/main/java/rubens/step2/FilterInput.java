package rubens.step2;

public class FilterInput {
	
	public FilterInput(Integer decade, String outputPath) {
		this.decade = decade;
		this.outputPath = outputPath;
	}

	private Integer decade;
	private String outputPath;

	public Integer getDecade() {
		return decade;
	}

	public void setDecade(Integer decade) {
		this.decade = decade;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

}

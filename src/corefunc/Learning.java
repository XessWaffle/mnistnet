package corefunc;

public interface Learning {
	public void computeAndPush(double[] inputError);
	public void computeAndPush(double error);
	public void adjust();
}

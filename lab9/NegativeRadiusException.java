public class NegativeRadiusException extends CircleException{

	private double radius;

	public NegativeRadiusException(double radius){
		super("negative radius"); //double?
		this.radius = radius;

	}

	public double radius(){
		return this.radius;
	}
}
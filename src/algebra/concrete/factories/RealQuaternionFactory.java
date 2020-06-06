package algebra.concrete.factories;

import algebra.concrete.RealQuaternion;
import algebra.factory.UnitaryRingFactory;

public class RealQuaternionFactory implements UnitaryRingFactory<RealQuaternion> {
	private static final RealQuaternionFactory INSTANCE = new RealQuaternionFactory();
	
	public static RealQuaternionFactory getInstance() {
		return INSTANCE;
	}
	
	@Override
	public RealQuaternion getAdditiveIdentity() {
		return new RealQuaternion(0,0,0,0);
	}

	@Override
	public RealQuaternion getMultiplicativeIdentity() {
		return new RealQuaternion(1,0,0,0);
	}
	
	@Override
	public RealQuaternion parse(String input) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static RealQuaternion fromEulerAngles(double[] angles) {
		if (angles.length != 3) 
			throw new IllegalArgumentException("Euler angles needs 3 components (yaw, pitch, roll)");
		return fromEulerAngles(angles[0], angles[1], angles[2]);
	}
	
	public static RealQuaternion fromEulerAngles(double yaw, double pitch, double roll) {	// yaw (Z), pitch (Y), roll (X)
		double cy = Math.cos(yaw * 0.5), 
				sy = Math.sin(yaw * 0.5),
				cp = Math.cos(pitch * 0.5),
				sp = Math.sin(pitch * 0.5),
				cr = Math.cos(roll * 0.5),
				sr = Math.sin(roll * 0.5);
	    return new RealQuaternion(
	    		cr * cp * cy + sr * sp * sy,
	    		sr * cp * cy - cr * sp * sy,
	    		cr * sp * cy + sr * cp * sy,
	    		cr * cp * sy - sr * sp * cy
	    );
	}

}

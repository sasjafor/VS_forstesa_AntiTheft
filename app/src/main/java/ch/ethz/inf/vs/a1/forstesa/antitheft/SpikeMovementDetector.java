package ch.ethz.inf.vs.a1.forstesa.antitheft;

import ch.ethz.inf.vs.a1.forstesa.antitheft.AlarmCallback;

public class SpikeMovementDetector extends AbstractMovementDetector {

    public SpikeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
		// TODO, insert your logic here
        return false;
    }
}

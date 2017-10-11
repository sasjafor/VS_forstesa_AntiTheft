package ch.ethz.inf.vs.a1.forstesa.antitheft;

public class SpikeMovementDetector extends AbstractMovementDetector {

    public SpikeMovementDetector(AlarmCallback callback, int sensitivity) {
        super(callback, sensitivity);
    }

    private int det;
    private boolean firstMov = false;

    public void setDetector(int detector) {
        det = detector;
    }

    public void setMov(boolean mov) {
        firstMov = mov;
    }

    public boolean getMov() {
        return firstMov;
    }

    @Override
    public boolean doAlarmLogic(float[] values) {
        if (det == 0) {
            float sum = 0;
            for (float i : values) sum += Math.abs(i);
            if (sum >= this.sensitivity) return true;
        } else {
            float max = 5;
            boolean containsMax = false;
            for (float i : values) containsMax = (containsMax || (Math.abs(i)) >= max);
            if (containsMax) return true;
        }
        return false;
    }
}
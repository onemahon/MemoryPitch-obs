package dsp;

import jdm.memorypitch.PeakData;

public interface AudioProcessingListener {

    void onDrawableFFTSignalAvailable(double[] absSignal);
    void onPeakFound(PeakData peak);
}

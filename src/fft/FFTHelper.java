package fft;

import jdm.memorypitch.PeakData;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class FFTHelper {
	
	private double mPeakFreq;
	private int mPeakPos;
	private double mSampleRateInHz;
	private int mNumberOfFFTPoints;
	private double mMaxFFTSample;	
	private double[] mSignal;
	private double[] mAbsSignal;
	
	public FFTHelper(double sampleRate, int numberOfFFTPoints) {
		mSampleRateInHz = sampleRate;
		mNumberOfFFTPoints = numberOfFFTPoints;
		mSignal = new double[numberOfFFTPoints*2];
		mAbsSignal = new double[numberOfFFTPoints];
		
		fft = new DoubleFFT_1D(mNumberOfFFTPoints);
	}
	
	private DoubleFFT_1D fft;
	public double[] calculateFFT(byte[] signal) {		
		double temp;
		
		for(int i = 0; i < mNumberOfFFTPoints; i++) {
			if((2*i+1) < signal.length) {
				temp = (double)((signal[2*i] & 0xFF) | (signal[2*i+1] << 8)) / 32768.0F;
				mSignal[i] = temp;
			} else {
				mSignal[i] = 0.0;
			}
		}

		fft.realForwardFull(mSignal);
		
		mMaxFFTSample = 0.0;
		mPeakPos = 0;
		for(int i = 0; i < mNumberOfFFTPoints; i++)
		{
			mAbsSignal[i] = Math.abs(mSignal[i]);
			 
			 if(mAbsSignal[i] > mMaxFFTSample)
			 {
				 mMaxFFTSample = mAbsSignal[i];
				 mPeakPos = i;
			 }
		}
		
		return mAbsSignal;
	}
	
	public double getPeakFrequency() {
		mPeakFreq = mPeakPos*(mSampleRateInHz/(mNumberOfFFTPoints*2));
		return mPeakFreq;
	}
	
	public double getPeakPower() {
		return mAbsSignal[mPeakPos];
	}
	
	public double getPeakCertainty() {
		// TODO make this actually find the certainty based on surrounding peaks in absSignal
		return 1;
	}
	
	public int getPeakFrequencyPosition() {
		return mPeakPos;
	}
	
	public double getPeakFrequency(int[] absSignal) {
		
		int peakPos = 0, max = absSignal[0];
		
		for(int i=1; i < (mNumberOfFFTPoints/2); i++)
		{
			 if(absSignal[i] > max)
			 {
				 max = absSignal[i];
				 peakPos = i;
			 }
		}
		
		return peakPos*(mSampleRateInHz/mNumberOfFFTPoints);
	}
	
	public double getMaxFFTSample() {
		return mMaxFFTSample;
	}
}

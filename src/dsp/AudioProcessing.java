package dsp;

import jdm.memorypitch.PeakData;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;
import fft.FFTHelper;

public class AudioProcessing extends Thread {
	
	private static final String TAG = AudioProcessing.class.getSimpleName();
	
	private double mSampleRateInHz;
	private int mNumberOfFFTPoints;
	
	private AudioRecord mRecorder;
	private int mMinBufferSize;
	
	private boolean mStopped;
	
	private static AudioProcessingListener mListener;
	
	private FFTHelper mFFT;
	
	public AudioProcessing(double sampleRate, int numberOfFFTPoints) {
		mSampleRateInHz = sampleRate;
		mNumberOfFFTPoints = numberOfFFTPoints;
		mFFT = new FFTHelper(mSampleRateInHz,mNumberOfFFTPoints);
	}
		
	@Override
	public void run() {
		runWithAudioRecord();
	}
	
	private byte[] tempBuffer;
	private void runWithAudioRecord() { // REAL_MODE - BUILT IN AUDIO DEVICE
		int numberOfReadBytes = 0;
		double[] absNormalizedSignal;
		
		mMinBufferSize = AudioRecord.getMinBufferSize((int)mSampleRateInHz,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
		mRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, (int)mSampleRateInHz, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, 10*mMinBufferSize);
		
		if(mRecorder==null) {
			throw new RuntimeException("Audio Recording Device was not initialized!!!");
		}

		mRecorder.startRecording();
		
		tempBuffer = new byte[2*mNumberOfFFTPoints];
		while(!mStopped) {
			numberOfReadBytes = mRecorder.read(tempBuffer,0,2*mNumberOfFFTPoints);
//			Log.d("processing", "example tempBuffer element: "+tempBuffer[15]);
			if(numberOfReadBytes > 0) {
				if(mFFT!=null) {
					absNormalizedSignal = mFFT.calculateFFT(tempBuffer);
					double freq = mFFT.getPeakFrequency();
					double pow = mFFT.getPeakPower();
					double cert = mFFT.getPeakCertainty();
					PeakData peak = new PeakData(freq, pow, cert);
					
					notifyListenersOnPeakFound(peak);
				}
			} else {
				Log.e(TAG,"There was an error reading the audio device - ERROR: "+numberOfReadBytes);
			}
		}
        
        mRecorder.stop();
        mRecorder.release();
	}
		
	public double getPeakFrequency(int[] absSignal) {
		return mFFT.getPeakFrequency(absSignal);
	}
	
	public double getMaxFFTSample() {
		return mFFT.getMaxFFTSample();
	}
	
	public int getPeakFrequencyPosition() {
		return mFFT.getPeakFrequencyPosition();
	}
	
	public void close() { 
		mStopped = true;
	}
	
	public void registerDrawableFFTSamplesAvailableListener(AudioProcessingListener listener) {
		mListener = listener;
	}
	
	public static void unregisterDrawableFFTSamplesAvailableListener() {
		mListener = null;
	}
	
	public void notifyListenersOnFFTSamplesAvailable(double[] absSignal) {
		if(!mStopped) {
			if(mListener!=null) {
				mListener.onDrawableFFTSignalAvailable(absSignal);
			}
		}
	}
	
	public void notifyListenersOnPeakFound(PeakData peak) {
		if(!mStopped) {			
			if (mListener != null) {
				mListener.onPeakFound(peak);
			}
		}
		
	}
	
}

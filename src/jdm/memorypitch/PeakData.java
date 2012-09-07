package jdm.memorypitch;

public class PeakData {
	
	public class PitchesFlats {
		public static final double A = 440.000f, 
								   Ab = 415.305f, 
								   G = 391.995f, 
								   Gb = 369.994f, 
							 	   F = 349.228f, 
								   E = 329.628f, 
								   Eb = 311.127f, 
								   D = 293.665f, 
								   Db = 277.183f, 
								   C = 261.626f, 
								   B = 246.942f, 
								   Bb = 233.082f;
	}
	
	public class PitchesSharps {
		public static final double A = 440.000f, 
							 	   Gs = 415.305f, 
							 	   G = 391.995f, 
							 	   Fs = 369.994f, 
							 	   F = 349.228f, 
							 	   E = 329.628f, 
							 	   Ds = 311.127f, 
							 	   D = 293.665f, 
							 	   Cs = 277.183f, 
							 	   C = 261.626f, 
							 	   B = 246.942f, 
							 	   As = 233.082f;
	}
	
	
	public double frequency, power, certainty;
	public float flatNote;
	public float sharpNote;
	
	public PeakData(double freq, double pow, double cert) {
		frequency = freq;
		power = pow;
		certainty = cert;
		
		setNotes();
	}
	
	public void setNotes() {
		double freq = frequency;
		if (freq > 440) {
			while (freq > 440) freq /= 2;
		} else {
			while (freq < 440) freq *= 2;
		}
		
		/*
		 * find the closest pitch based on the new "freq" variable
		 */
		double flatPitch = findClosestFlatPitch(freq);
		
		
	}
	
	/**
	 * Designed to be called once you have a frequency that is found within the standard bounds of 233 to 440 Hz.
	 * @param frequency
	 */
	private double findClosestFlatPitch(double frequency) {		
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.B && frequency < PitchesFlats.C)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.Bb && frequency < PitchesFlats.B)
			return PitchesFlats.Bb;
		
		else return PitchesFlats.A;
		
		
	}
	
	
}

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
	public double pitchNote;
	
	public PeakData(double freq, double pow, double cert) {
		frequency = freq;
		power = pow;
		certainty = cert;
		pitchNote = getPitch(freq);
	}
	
	public double getPitch(double freq) {
		if (freq > 440) {
			while (freq > 440) freq /= 2;
		} else if (freq < 220){
			while (freq < 220) freq *= 2;
		}
		
		return getRoundedPitch(freq);	
	}
	
	/**
	 * Designed to be called once you have a frequency that is found within the standard bounds of 233 to 440 Hz.
	 * @param frequency
	 */
	private double getRoundedPitch(double frequency) {
		// TODO not 100% accurate, since we're assuming the pitch is anywhere ABOVE the given frequency
		if (frequency > PitchesFlats.Bb && frequency <= PitchesFlats.B)
			return PitchesFlats.Bb;
		if (frequency > PitchesFlats.B && frequency <= PitchesFlats.C)
			return PitchesFlats.B;
		if (frequency > PitchesFlats.C && frequency <= PitchesFlats.Db)
			return PitchesFlats.C;
		if (frequency > PitchesFlats.Db && frequency <= PitchesFlats.D)
			return PitchesFlats.Db;
		if (frequency > PitchesFlats.D && frequency <= PitchesFlats.Eb)
			return PitchesFlats.D;
		if (frequency > PitchesFlats.Eb && frequency <= PitchesFlats.E)
			return PitchesFlats.Eb;
		if (frequency > PitchesFlats.E && frequency <= PitchesFlats.F)
			return PitchesFlats.E;
		if (frequency > PitchesFlats.F && frequency <= PitchesFlats.Gb)
			return PitchesFlats.F;
		if (frequency > PitchesFlats.Gb && frequency <= PitchesFlats.G)
			return PitchesFlats.Gb;
		if (frequency > PitchesFlats.G && frequency <= PitchesFlats.Ab)
			return PitchesFlats.G;
		if (frequency > PitchesFlats.Ab && frequency <= PitchesFlats.A)
			return PitchesFlats.Ab;
		if (frequency > PitchesFlats.A && frequency <= PitchesFlats.Bb)
			return PitchesFlats.A;
		return -1;
	}
	
	public String getFlatPitchLetter() {
		if (pitchNote == PitchesFlats.A) return "A";
		if (pitchNote == PitchesFlats.Ab) return "Ab";
		if (pitchNote == PitchesFlats.B) return "B";
		if (pitchNote == PitchesFlats.Bb) return "Bb";
		if (pitchNote == PitchesFlats.C) return "C";
		if (pitchNote == PitchesFlats.D) return "D";
		if (pitchNote == PitchesFlats.Db) return "Db";
		if (pitchNote == PitchesFlats.E) return "E";
		if (pitchNote == PitchesFlats.Eb) return "Eb";
		if (pitchNote == PitchesFlats.F) return "F";
		if (pitchNote == PitchesFlats.G) return "G";
		if (pitchNote == PitchesFlats.Gb) return "Gb";
		
		return "";
	}
	
	
}

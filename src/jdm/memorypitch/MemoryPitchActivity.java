package jdm.memorypitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import dsp.AudioProcessing;
import dsp.AudioProcessingListener;

public class MemoryPitchActivity extends Activity {
	private TextView pitchOutput, pitchLetter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.memory_pitch_activity);

		pitchOutput = (TextView) findViewById(R.id.pitch_output_button);
		pitchLetter = (TextView) findViewById(R.id.pitch_output_letter);
		
		buildProcessor();
		
		Button recordButton = (Button) findViewById(R.id.record_button);
		recordButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!isRecording)
					beginRecording();
				else
					endRecording();
			}
		});
	}
	
	private void buildProcessor() {
		processor = new AudioProcessing(8000, 1024);
		// TODO need a window function?
		// audioAnalyser.setWindowFunc(Window.Function.BLACKMAN_HARRIS);
		
		processor.registerDrawableFFTSamplesAvailableListener(new AudioProcessingListener() {
			public void onDrawableFFTSignalAvailable(double[] absSignal) {
				// do nothing
			}
			
			public void onPeakFound(final PeakData peak) {
				runOnUiThread(new Runnable() {
					public void run() {
						pitchOutput.setText(String.format("%.1f", peak.frequency)+" hz");
						pitchLetter.setText(peak.getFlatPitchLetter());
					}
				});
			}
		});
	}

	private AudioProcessing processor;
	private boolean isRecording = false;
	public void beginRecording() {
		isRecording = true;
		
		buildProcessor();
		
		processor.start();
		
		Toast.makeText(getApplicationContext(), "Recording began!", Toast.LENGTH_SHORT).show();
	}
	
	public void endRecording() {
		isRecording = false;
		
		processor.close();
		
		Toast.makeText(getApplicationContext(), "Recording ended....", Toast.LENGTH_SHORT).show();
	}
	
}
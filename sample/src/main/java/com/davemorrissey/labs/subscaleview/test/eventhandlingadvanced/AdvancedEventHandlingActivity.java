package com.davemorrissey.labs.subscaleview.test.eventhandlingadvanced;

import static com.davemorrissey.labs.subscaleview.test.R.layout.pages_activity;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p1_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p1_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p2_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p2_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p3_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p3_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p4_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p4_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p5_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_p5_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.advancedevent_title;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.davemorrissey.labs.subscaleview.test.AbstractPagesActivity;
import com.davemorrissey.labs.subscaleview.test.Page;
import com.davemorrissey.labs.subscaleview.test.R.id;

import java.util.Arrays;

public class AdvancedEventHandlingActivity extends AbstractPagesActivity {

	public AdvancedEventHandlingActivity() {
		super(advancedevent_title, pages_activity, Arrays.asList(
			new Page(advancedevent_p1_subtitle, advancedevent_p1_text),
			new Page(advancedevent_p2_subtitle, advancedevent_p2_text),
			new Page(advancedevent_p3_subtitle, advancedevent_p3_text),
			new Page(advancedevent_p4_subtitle, advancedevent_p4_text),
			new Page(advancedevent_p5_subtitle, advancedevent_p5_text)
		));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final SubsamplingScaleImageView imageView = findViewById(id.imageView);
		final GestureDetector gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				if (imageView.isReady()) {
					PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
					Toast.makeText(AdvancedEventHandlingActivity.this, "Single tap: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AdvancedEventHandlingActivity.this, "Single tap: Image not ready", Toast.LENGTH_SHORT).show();
				}
				return true;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				if (imageView.isReady()) {
					PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
					Toast.makeText(getApplicationContext(), "Long press: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "Long press: Image not ready", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if (imageView.isReady()) {
					PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
					Toast.makeText(getApplicationContext(), "Double tap: " + ((int) sCoord.x) + ", " + ((int) sCoord.y), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getApplicationContext(), "Double tap: Image not ready", Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});

		imageView.setImage(ImageSource.asset("sanmartino.jpg"));
		imageView.setOnTouchListener((view, motionEvent) -> gestureDetector.onTouchEvent(motionEvent));
	}

}

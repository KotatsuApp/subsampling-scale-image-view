package com.davemorrissey.labs.subscaleview.test.animation;

import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.OnClickListener;
import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.PAN_LIMIT_CENTER;
import static com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView.PAN_LIMIT_INSIDE;
import static com.davemorrissey.labs.subscaleview.test.R.layout.animation_activity;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p1_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p1_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p2_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p2_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p3_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p3_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p4_subtitle;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_p4_text;
import static com.davemorrissey.labs.subscaleview.test.R.string.animation_title;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.annotation.Nullable;

import com.davemorrissey.labs.subscaleview.AnimationBuilder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.test.AbstractPagesActivity;
import com.davemorrissey.labs.subscaleview.test.Page;
import com.davemorrissey.labs.subscaleview.test.R.id;
import com.davemorrissey.labs.subscaleview.test.extension.views.PinView;

import java.util.Arrays;
import java.util.Random;

public class AnimationActivity extends AbstractPagesActivity {

	private PinView view;

	public AnimationActivity() {
		super(animation_title, animation_activity, Arrays.asList(
			new Page(animation_p1_subtitle, animation_p1_text),
			new Page(animation_p2_subtitle, animation_p2_text),
			new Page(animation_p3_subtitle, animation_p3_text),
			new Page(animation_p4_subtitle, animation_p4_text)
		));
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViewById(id.play).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AnimationActivity.this.play();
			}
		});
		view = findViewById(id.imageView);
		view.setImage(ImageSource.asset("sanmartino.jpg"));
	}

	@Override
	protected void onPageChanged(int page) {
		if (page == 2) {
			view.setPanLimit(PAN_LIMIT_CENTER);
		} else {
			view.setPanLimit(PAN_LIMIT_INSIDE);
		}
	}

	private void play() {
		Random random = new Random();
		if (view.isReady()) {
			float maxScale = view.getMaxScale();
			float minScale = view.getMinScale();
			float scale = (random.nextFloat() * (maxScale - minScale)) + minScale;
			PointF center = new PointF(random.nextInt(view.getSWidth()), random.nextInt(view.getSHeight()));
			view.setPin(center);
			AnimationBuilder animationBuilder = view.animateScaleAndCenter(scale, center);
			if (animationBuilder == null) {
				return;
			}
			if (getPage() == 3) {
				animationBuilder.withDuration(2000)
					.withInterpolator(new AnticipateOvershootInterpolator())
					.withInterruptible(false)
					.start();
			} else {
				animationBuilder.withDuration(750).start();
			}
		}
	}

}

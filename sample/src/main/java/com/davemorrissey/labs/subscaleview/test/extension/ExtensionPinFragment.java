package com.davemorrissey.labs.subscaleview.test.extension;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.test.R.id;
import com.davemorrissey.labs.subscaleview.test.R.layout;
import com.davemorrissey.labs.subscaleview.test.extension.views.PinView;

public class ExtensionPinFragment extends Fragment {

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(layout.extension_pin_fragment, container, false);
		final ExtensionActivity activity = (ExtensionActivity) getActivity();
		if (activity != null) {
			rootView.findViewById(id.next).setOnClickListener(v -> activity.next());
		}
		PinView imageView = rootView.findViewById(id.imageView);
		imageView.setImage(ImageSource.asset("sanmartino.jpg"));
		imageView.setPin(new PointF(1602f, 405f));
		return rootView;
	}

}

package com.whming.widget;

import android.animation.TypeEvaluator;
import android.annotation.TargetApi;
import android.graphics.PointF;
import android.os.Build;

/**
 * Created by gavin on 15-4-2.
 * http://gavinliu.cn/2015/03/30/Android-Animation-贝塞尔曲线之美/
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BezierEvaluator implements TypeEvaluator<PointF> {

	private PointF points[];

	public BezierEvaluator(PointF... points) {
		if (points.length != 3) {
			throw new IllegalArgumentException("只演示三次方贝赛尔曲线");
		}
		this.points = points;
	}

	/**
	 * B(t) = P0 + (P1 - P0) * t = (1 - t) * P0 + t * P1, t ∈ [0, 1] B(t) = (1 -
	 * t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1] B(t) = P0 * (1-t)^3 +
	 * 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3
	 * 
	 * B(t) = P0 * (1 - t)^2 + 2 * P1 * t * (1 - t) + P2 * t^2, t ∈ [0,1] B(t) =
	 * P0 * (1 - t)^3 + 3 * P1 * t * (1 - t)^2 + P2 * t^2 * 3 * (1-t) + P3 * t^3
	 * 
	 */
	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		// B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) +
		// P3 * t^3

		float t = fraction;
		float one_t = 1.0f - t;

		PointF P0 = points[0];
		PointF P1 = points[1];
		PointF P2 = points[2];

		// float x = (float) (P0.x * Math.pow(one_t, 3) + 3 * P1.x * t *
		// Math.pow(one_t, 2) + 3 * P2.x * Math.pow(t, 2) * one_t + P3.x *
		// Math.pow(t, 3));
		// float y = (float) (P0.y * Math.pow(one_t, 3) + 3 * P1.y * t *
		// Math.pow(one_t, 2) + 3 * P2.y * Math.pow(t, 2) * one_t + P3.y *
		// Math.pow(t, 3));

		float x = (float) (P0.x * Math.pow(one_t, 2) + 2 * P1.x * t
				* Math.pow(one_t, 1) + P2.x * Math.pow(t, 2));
		float y = (float) (P0.y * Math.pow(one_t, 2) + 2 * P1.y * t
				* Math.pow(one_t, 1) + P2.y * Math.pow(t, 2));

		PointF pointF = new PointF(x, y);

		return pointF;
	}

}

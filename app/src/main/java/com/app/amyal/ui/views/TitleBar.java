package com.app.amyal.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.amyal.R;

public class TitleBar extends RelativeLayout {

	private TextView txtTitle;
	private ImageView btnLeft;
	private ImageView btnRight2;
	private ImageView btnRight;
	private AnyTextView txtBadge;
	private RelativeLayout header_layout;
	private ImageView ivTitle;

	private View.OnClickListener menuButtonListener;
	private OnClickListener backButtonListener;
	private OnClickListener notificationButtonListener;

	private Context context;


	public TitleBar(Context context) {
		super(context);
		this.context = context;
		initLayout(context);
	}

	public TitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout(context);
		if (attrs != null)
			initAttrs(context, attrs);
	}

	public TitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initLayout(context);
		if (attrs != null)
			initAttrs(context, attrs);
	}

	private void initAttrs(Context context, AttributeSet attrs) {
	}

	private void bindViews() {

		txtTitle = (TextView) this.findViewById(R.id.txt_subHead);
		btnRight = (ImageView) this.findViewById(R.id.btnRight);
		btnRight2 = (ImageView) this.findViewById(R.id.btnRight2);
		btnLeft = (ImageView) this.findViewById(R.id.btnLeft);
		txtBadge = (AnyTextView) findViewById(R.id.txtBadge);
		header_layout = (RelativeLayout) findViewById(R.id.header_layout);
		ivTitle = (ImageView) findViewById(R.id.ivTitle);

	}

	public void setTitleBarBackground(int id){

		header_layout.setBackgroundColor(id);

	}

	private void initLayout(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.header_main, this);
		bindViews();
	}

	public void hideButtons() {
		txtTitle.setVisibility(View.INVISIBLE);
		ivTitle.setVisibility(View.INVISIBLE);
		btnLeft.setVisibility(View.INVISIBLE);
		btnRight.setVisibility(View.INVISIBLE);
		btnRight2.setVisibility(View.INVISIBLE);
		txtBadge.setVisibility(View.GONE);

	}

	public void showBackButton() {
		btnLeft.setVisibility(View.VISIBLE);
		btnLeft.setImageResource(R.drawable.back);
		btnLeft.setOnClickListener(backButtonListener);

	}

	public void showMenuButton() {
		btnLeft.setVisibility(View.VISIBLE);
		btnLeft.setOnClickListener(menuButtonListener);
		btnLeft.setImageResource(R.drawable.sidebar);
	}

	public void showRightButton(int id,OnClickListener onClickListener) {
		btnRight2.setVisibility(View.VISIBLE);
		btnRight2.setOnClickListener(onClickListener);
		btnRight2.setImageResource(id);
	}

	public void showRight1Button(int id,OnClickListener onClickListener) {
		btnRight.setVisibility(View.VISIBLE);
		btnRight.setOnClickListener(onClickListener);
		btnRight.setImageResource(id);
	}

	public void setSubHeading(String heading) {
		ivTitle.setVisibility(GONE);
		txtTitle.setVisibility(View.VISIBLE);
		txtTitle.setSelected(true);
		txtTitle.setText(heading);

	}

	public void setSubHeadingImage(int id) {
		txtTitle.setVisibility(GONE);
		ivTitle.setVisibility(VISIBLE);
		ivTitle.setImageResource(id);

	}

	public void showNotificationButton(int Count) {
		btnRight.setVisibility(View.INVISIBLE);
		btnRight2.setVisibility(View.VISIBLE);
		btnRight2.setOnClickListener(notificationButtonListener);
		btnRight2.setImageResource(R.drawable.notification);
		if (Count > 0) {
			txtBadge.setVisibility(View.VISIBLE);
			txtBadge.setText(Count + "");
		} else {
			txtBadge.setVisibility(View.GONE);
		}

	}



	public void showTitleBar() {
		this.setVisibility(View.VISIBLE);
	}

	public void hideTitleBar() {
		this.setVisibility(View.GONE);
	}

	public void setMenuButtonListener(View.OnClickListener listener) {
		menuButtonListener = listener;
	}

	public void setBackButtonListener(View.OnClickListener listener) {
		backButtonListener = listener;
	}

	public void setNotificationButtonListener(View.OnClickListener listener) {
		notificationButtonListener = listener;
	}



}

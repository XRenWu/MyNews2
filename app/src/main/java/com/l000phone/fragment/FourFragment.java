package com.l000phone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.l000phone.mynews.R;

/**
 * Created by Administrator on 2016/10/30.
 */

public class FourFragment extends Fragment {

	private TextView mtv;
	private String content;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		Bundle arguments = getArguments();
		content = arguments.getString("content", "");
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.myfragment_item, null);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}


}

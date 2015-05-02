package com.sunshine.sihuo.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sunshine.sihuo.R;

/**
 * Created by PLJ on 2015/5/2.
 */
public class PhotoFragment extends Fragment {
    private static final int OPEN_RESULT = 1;
    private ImageView iv;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.photo, null);
        iv = (ImageView) view.findViewById(R.id.photo_iv);

        Intent intent = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_RESULT);
        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        Bitmap bm = (Bitmap) bundle.get("data");
        // System.out.println(bm.getByteCount());

        iv.setImageBitmap(bm);

    }
}

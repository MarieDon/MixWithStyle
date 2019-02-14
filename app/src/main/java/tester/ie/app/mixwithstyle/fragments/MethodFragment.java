package tester.ie.app.mixwithstyle.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tester.ie.app.mixwithstyle.R;

public class MethodFragment extends Fragment {
    private View view;
    private TextView methodText;

    public MethodFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.method, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        methodText = view.findViewById(R.id.method_text);

    }
}

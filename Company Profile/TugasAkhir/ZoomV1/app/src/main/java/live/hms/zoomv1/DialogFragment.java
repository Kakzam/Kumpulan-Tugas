package live.hms.zoomv1;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import live.hms.zoomv1.databinding.FragmentDialogBinding;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

//    private String mParam1;
//    private String mParam2;

    public static DialogFragment newInstance(String param1, String param2) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    FragmentDialogBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDialogBinding.inflate(getLayoutInflater());

        if (getArguments() != null){
            binding.title.setText(getArguments().getString(ARG_PARAM1));
            binding.message.setText(getArguments().getString(ARG_PARAM2));
        } else Toast.makeText(getContext(), "Arguments Kosong", Toast.LENGTH_SHORT).show();

        binding.dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        return binding.getRoot();
    }
}
package com.catering.dessert.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.catering.dessert.R;
import com.catering.dessert.Storage;
import com.catering.dessert.ui.item.ItemOrder;
import com.catering.dessert.ui.sheet.DialogOrder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment implements ItemOrder.onListener {

    View view;
    ProgressBar progress;
    TextView textMessage;
    EditText editSearch;
    ImageView imageClear, imageSearch;
    CardView cardSearch;
    RecyclerView recycler;

    List<String> id = new ArrayList<>();
    List<String> name = new ArrayList<>();
    List<String> phone = new ArrayList<>();
    List<String> address = new ArrayList<>();
    List<String> nik = new ArrayList<>();
    List<Integer> total = new ArrayList<>();
    List<Integer> pesanan = new ArrayList<>();

    ItemOrder item;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);

        progress = view.findViewById(R.id.fragment_order_progressbar);
        textMessage = view.findViewById(R.id.fragment_order_message);
        editSearch = view.findViewById(R.id.fragment_order_edit);
        imageSearch = view.findViewById(R.id.fragment_order_search);
        imageClear = view.findViewById(R.id.fragment_order_clear);
        cardSearch = view.findViewById(R.id.fragment_order_card);
        recycler = view.findViewById(R.id.fragment_order_recycler);

        if (new Storage(getContext()).getLogin()) {
            FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progress.setVisibility(View.GONE);

                    for (DocumentSnapshot document : task.getResult().getDocuments()) {
                        FirebaseFirestore.getInstance().collection("catering_dessert").document("user").collection("-").document(document.getId()).collection("-").get().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                int dTotal = 0;
                                progress.setVisibility(View.GONE);
                                for (DocumentSnapshot total : task1.getResult().getDocuments())
                                    dTotal += Integer.parseInt(total.get("total").toString());

                                if (task1.getResult().getDocuments().size() > 0) {
                                    id.add(document.getId());
                                    name.add(document.get("name").toString());
                                    phone.add(document.get("phone").toString());
                                    address.add(document.get("address").toString());
                                    nik.add(document.get("nik").toString());
                                    total.add(dTotal);
                                    pesanan.add(task1.getResult().getDocuments().size());

                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    recycler.setLayoutManager(layoutManager);
                                    item = new ItemOrder(id, name, phone, address, nik, total, pesanan, OrderFragment.this);
                                    recycler.setAdapter(item);
                                }
                            } else textMessage.setText("Silahkan tekan tombol simpan kembali");
                        });
                    }
                } else textMessage.setText("Silahkan tekan tombol simpan kembali");
            });
        } else {
            recycler.setVisibility(View.GONE);
            cardSearch.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onClick(int position, Boolean select) {
        if (select) {
            Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            contactIntent.putExtra(ContactsContract.Intents.Insert.NAME, name.get(position));
            contactIntent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.get(position));
            startActivity(contactIntent);
        } else {
            DialogOrder order = new DialogOrder();
            Bundle bundle = new Bundle();
            bundle.putString("ID", id.get(position));
            order.setArguments(bundle);
            order.show(getActivity().getSupportFragmentManager(), "DialogOrder");
        }
    }
}
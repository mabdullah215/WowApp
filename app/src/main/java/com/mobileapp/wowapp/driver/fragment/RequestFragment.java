package com.mobileapp.wowapp.driver.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.RequestMenu;
import com.mobileapp.wowapp.driver.adapter.RequestListAdapter;
import com.mobileapp.wowapp.model.SystemRequest;

import java.util.ArrayList;

public class RequestFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_appointment, container, false);
        RequestMenu menu=view.findViewById(R.id.req_menu);
        RecyclerView recyclerView=view.findViewById(R.id.data_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        TextView tvEmpty=view.findViewById(R.id.tv_empty);

        ArrayList<SystemRequest>requests=new ArrayList<>();
        RequestListAdapter requestListAdapter=new RequestListAdapter(getActivity(),requests);
        recyclerView.setAdapter(requestListAdapter);

       /* requestRef.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                requests.clear();
                for(DocumentSnapshot snapshot: value.getDocuments())
                {
                    SystemRequest request=snapshot.toObject(SystemRequest.class);
                    requests.add(request);
                }

                requestListAdapter.updateList(requests);

                if(requests.isEmpty())
                {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvEmpty.setVisibility(View.GONE);
                }

            }
        });

        offersRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                offers.clear();
                for(DocumentSnapshot snapshot: value.getDocuments())
                {
                    Offers offer=snapshot.toObject(Offers.class);
                    offers.add(offer);
                }

                offerListAdapter.updateList(offers);

                if(offers.isEmpty())
                {
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                else
                {
                    tvEmpty.setVisibility(View.GONE);
                }
            }
        });
*/

        menu.setUpdateListener(new RequestMenu.PositionUpdateListener() {
            @Override
            public void onPositionUpdate(int pos)
            {
                if(pos==0)
                {
                    recyclerView.setAdapter(requestListAdapter);
                }
                else
                {
                    //recyclerView.setAdapter(offerListAdapter);
                }
            }
        });


        return view;
    }
}
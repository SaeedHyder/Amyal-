package com.app.amyal.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.amyal.R;
import com.app.amyal.activities.MainActivity;
import com.app.amyal.entities.HotelGuestDetailEnt;
import com.app.amyal.fragments.AddGuestFragment;
import com.app.amyal.interfaces.ItemClickListener;
import com.app.amyal.ui.views.AnyTextView;
import com.app.amyal.ui.views.AnyTextView1;

import java.util.ArrayList;

import static com.app.amyal.activities.MainActivity.hotelGuestDetailEnts;

/**
 * Created by Addi.
 */

public class GuestinfoAdapter extends RecyclerView.Adapter<GuestinfoAdapter.VH> implements ItemClickListener {
    ArrayList<Integer> totGuests;
    ItemClickListener guestInfoIterface;
    LayoutInflater inflater;
    MainActivity context;
    AnyTextView1 tvGuestDetails;
    AnyTextView btnAddGuest;

    public GuestinfoAdapter(MainActivity context, ItemClickListener guestInfoIterface, ArrayList<Integer> totGuests) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.guestInfoIterface = guestInfoIterface;
        this.totGuests = totGuests;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_guest_info, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {

//        if (infoList != null && infoList.get(position) != null) {
//            tvGuestDetails.setText(infoList.get(position));
//        }

        if (hotelGuestDetailEnts.size() > position && hotelGuestDetailEnts.get(position) != null) {
            tvGuestDetails.setText(hotelGuestDetailEnts.get(position).getName() + " ," + hotelGuestDetailEnts.get(position).getEmail()); /*+ " ," + hotelGuestDetailEnts[guestNo].getCountry())*/
            btnAddGuest.setText(context.getString(R.string.edit));
        }

        btnAddGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGuestFragment addGuestFragment1 = AddGuestFragment.newInstance(true);
                addGuestFragment1.setItemClickListener(GuestinfoAdapter.this);
                addGuestFragment1.setGuestNo(position, totGuests.get(position));
                context.replaceDockableFragment(addGuestFragment1, AddGuestFragment.class.getSimpleName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return totGuests.size();
    }

    @Override
    public void itemClicked(Object object, int position) {

    }

    @Override
    public void itemClicked(Object object, boolean isfrom, int guestNo) {
        HotelGuestDetailEnt hotelGuestDetailEnt = (HotelGuestDetailEnt) object;
        if (hotelGuestDetailEnt != null) {
            if (hotelGuestDetailEnts.size() <= guestNo)
                hotelGuestDetailEnts.add((hotelGuestDetailEnt));
            else
                hotelGuestDetailEnts.add(guestNo, (hotelGuestDetailEnt));
        }
        guestInfoIterface.itemClicked(object, isfrom, guestNo);
        notifyDataSetChanged();
    }

    class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
            btnAddGuest = itemView.findViewById(R.id.btnAddGuest);
            tvGuestDetails = itemView.findViewById(R.id.tvGuestDetails);
        }
    }
}

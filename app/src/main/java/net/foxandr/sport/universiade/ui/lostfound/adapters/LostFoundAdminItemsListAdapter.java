package net.foxandr.sport.universiade.ui.lostfound.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.lostfound.model.LostFoundDTOResponse;
import net.foxandr.sport.universiade.utils.ImageDownloader;

import java.util.List;

public class LostFoundAdminItemsListAdapter extends ArrayAdapter<LostFoundDTOResponse> {

    private LayoutInflater inflater;
    private int layout;
    private List<LostFoundDTOResponse> lostFoundAdminItemsList;
    private boolean isRequests;


    public LostFoundAdminItemsListAdapter(Context context, int resource,
                                          List<LostFoundDTOResponse> lostFoundAdminItemsList,
                                          boolean isRequests) {
        super(context, resource, lostFoundAdminItemsList);
        this.inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.lostFoundAdminItemsList = lostFoundAdminItemsList;
        this.isRequests = isRequests;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView shortDescView = view.findViewById(R.id.lostfound_admin_short_description);
        TextView shortAreView = view.findViewById(R.id.lostfound_admin_short_area_info);
        TextView cityView = view.findViewById(R.id.lostfound_admin_city);
        ImageView itemImageView = view.findViewById(R.id.lostfound_admin_item_image);

        LostFoundDTOResponse lostFoundDTOResponse = lostFoundAdminItemsList.get(position);

        setTextWithSubstring(shortDescView, lostFoundDTOResponse.getItemDescription(), 70);
        setTextWithSubstring(shortAreView, lostFoundDTOResponse.getLostItemArea(), 70);
        cityView.setText(lostFoundDTOResponse.getCityName());
        ImageDownloader.getImageByUuid(itemImageView, lostFoundDTOResponse.getImagesEntity().getUuid());

        if (isRequests) {
            TextView nameView = view.findViewById(R.id.lostfound_admin_contact_name);
            TextView contactView = view.findViewById(R.id.lostfound_admin_contact_to_notify);
            nameView.setText(lostFoundDTOResponse.getLostFoundRequestsEntity().getContactName());
            contactView.setText(lostFoundDTOResponse.getLostFoundRequestsEntity().getContactToNotify());
        } else {
            LinearLayout lostfoundContactsPart = view.findViewById(R.id.lostfound_admin_request_layout);
            lostfoundContactsPart.setVisibility(View.GONE);
        }
        view.setBackgroundColor(position % 2 == 0 ? Color.WHITE : Color.parseColor("#DEDEDE"));
        return view;
    }

    public void setTextWithSubstring(TextView textView, String s, int count) {
        if (s.length() > count) {
            String newText = s.substring(0, count) + "...";
            textView.setText(newText);
        } else {
            textView.setText(s);
        }
    }
}

package se.mharrys.tdw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import se.mharrys.tdw.article.ArticleItem;

/**
 * The responsibility of this class is to adapt the {@link se.mharrys.article.ArticleItem} class
 * for a list view.
 */
class ArticleItemAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ArticleItem> items;

    public ArticleItemAdapter(Context context, List<ArticleItem> items) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ArticleItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;

        if (convertView == null) {
            row = inflater.inflate(android.R.layout.simple_list_item_1, null);
        } else {
            row = convertView;
        }

        ArticleItem current = getItem(position);
        TextView text = (TextView) row.findViewById(android.R.id.text1);
        text.setText(current.getTitle());

        return row;
    }
}
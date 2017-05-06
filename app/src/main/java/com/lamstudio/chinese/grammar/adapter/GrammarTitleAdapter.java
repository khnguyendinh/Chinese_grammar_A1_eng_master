package com.lamstudio.chinese.grammar.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lamstudio.chinese.grammar.R;
import com.lamstudio.chinese.grammar.activities.GrammarDetailActivity;
import com.lamstudio.chinese.grammar.object.GrammarObj;
import com.lamstudio.chinese.grammar.util.Constant;

import java.util.ArrayList;

/**
 * Created by Administrator PC on 11/23/2015.
 */
public class GrammarTitleAdapter extends RecyclerView.Adapter<GrammarTitleAdapter.HolderX> {

    private Context context;
    private ArrayList<GrammarObj> grammarObjs;
    LayoutInflater inflater;
    public GrammarTitleAdapter(ArrayList<GrammarObj> grammarObjs, Context context) {
        this.grammarObjs = grammarObjs;
        this.context = context;
    }

    @Override
    public HolderX onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.grammar_obj_title, parent, false);
        HolderX holder = new HolderX(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(HolderX holder, int position) {
        holder.nameLesson.setText(grammarObjs.get(position).getTitle());
        holder.idLesson.setText(context.getString(R.string.lesson)+" "+(position+1));
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return grammarObjs.size();
    }

    class HolderX extends RecyclerView.ViewHolder {
        TextView nameLesson;
        TextView idLesson;
        int pos;
        public HolderX(View itemView) {
            super(itemView);
            nameLesson = (TextView) itemView.findViewById(R.id.txt_story_title);
            idLesson = (TextView) itemView.findViewById(R.id.txt_lesson);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GrammarDetailActivity.class);
                    intent.putExtra(Constant.KEY_ID, grammarObjs.get(pos).getG_id());
                    context.startActivity(intent);
                }
            });
        }
    }

}


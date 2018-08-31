package br.edu.iff.pooa.trabalho04_2018_1.view;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import br.edu.iff.pooa.trabalho04_2018_1.R;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewHolder mViewHolder = new ViewHolder();
    private boolean isExpanded = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        mViewHolder.modelo = findViewById(R.id.spinnerModelo);
        mViewHolder.ano = findViewById(R.id.spinnerAno);
        mViewHolder.min = findViewById(R.id.edtMin);
        mViewHolder.max = findViewById(R.id.edtMax);
        mViewHolder.procurar = findViewById(R.id.buttonProcurar);
        mViewHolder.escondeMostra = findViewById(R.id.ivEsconderMostrar);
        mViewHolder.clPesquisa = findViewById(R.id.clPesquisa);

        mViewHolder.escondeMostra.setOnClickListener(this);
        mViewHolder.procurar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.buttonProcurar){

        }
        if (id == R.id.ivEsconderMostrar){
            if (isExpanded){
                unexpander();
                mViewHolder.escondeMostra.setImageResource(R.drawable.expand_more);
                isExpanded = false;
            } else{
                expander();
                mViewHolder.escondeMostra.setImageResource(R.drawable.expand_less);
                isExpanded = true;
            }
        }
    }

    public static class ViewHolder{
        EditText min, max;
        Spinner modelo, ano;
        ImageView escondeMostra;
        Button procurar;
        ConstraintLayout clPesquisa;
    }

    public void unexpander(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)mViewHolder.clPesquisa.getLayoutParams();
        layoutParams.height = 1;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mViewHolder.clPesquisa.setLayoutParams(layoutParams);
    }
    public void expander(){
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)mViewHolder.clPesquisa.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        mViewHolder.clPesquisa.setLayoutParams(layoutParams);
    }
}

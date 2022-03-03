package com.rahat.pro_hero.codingtest.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rahat.pro_hero.codingtest.R
import com.rahat.pro_hero.codingtest.databinding.ItemQuizOptionBinding
import com.rahat.pro_hero.codingtest.models.AnswerModel

class AnswerAdapter(
    private val interaction: Interaction? = null,
    list: List<AnswerModel>,
    showAnswer: Boolean = false
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var showAnswer = showAnswer
    private val clist = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return viewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_quiz_option,
                parent,
                false
            ),
            interaction,
            showAnswer
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is viewholder -> {
                clist[position].let { holder.bind(it) }
            }
        }
    }

    override fun getItemCount(): Int {
        return clist.size
    }

    fun getList(): List<AnswerModel> {
        return clist
    }


    class viewholder(
        itemView: View,
        private val interaction: Interaction?,
        showAnswer: Boolean,
    ) : RecyclerView.ViewHolder(itemView) {
        val showAns = showAnswer
        fun bind(item: AnswerModel) {
            val binding = ItemQuizOptionBinding.bind(itemView)
            binding.answerText.text = item.answer.toString()
            binding.cardContainer.strokeWidth = 12

            if (item.checker == 1) {
                binding.cardContainer.strokeColor =
                    ContextCompat.getColor(binding.root.context, R.color.red)
            }

            if (item.isRight && showAns) {
                binding.cardContainer.strokeColor =
                    ContextCompat.getColor(binding.root.context, R.color.green)
            }


//            if (item.isRight) {
//                binding.answerText.setBackgroundColor(Color.GREEN)
//            }

            itemView.setOnClickListener {

                if (!showAns) {
                    interaction?.onItemSelected(adapterPosition, item)
                }

            }
        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: AnswerModel)
    }
}

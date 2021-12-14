package ru.android.shift_education.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.android.shift_education.R
import ru.android.shift_education.databinding.ItemLoanBinding
import ru.android.shift_education.domain.entities.LoanEntity
import ru.android.shift_education.presentation.fragments.MainFragment

class MainAdapter(private var onItemViewClickListener: MainFragment.OnItemViewClickListener?): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var loanData: List<LoanEntity> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setLoan(data: List<LoanEntity>) {
        loanData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(loanData[position])
    }

    override fun getItemCount(): Int = loanData.size

    fun removeListener() {
        onItemViewClickListener = null
    }


    inner class MainViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {
        fun bind(loan: LoanEntity) {
            itemView.apply {
                findViewById<TextView>(R.id.tv_loan_status).text = loan.state
                findViewById<TextView>(R.id.tv_loan_amount).text = loan.amount.toString()
                setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(loan)
                }
            }
        }
    }
}

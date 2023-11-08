import androidx.recyclerview.widget.DiffUtil

class HistoryDiffCallback : DiffUtil.ItemCallback<Measurement>() {
    override fun areItemsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
        return oldItem == newItem
    }
}
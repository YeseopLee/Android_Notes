import android.widget.Filter
import android.widget.Filterable

class FilterableAdapter(val context: Context, val dataDTO : ArrayList<dataDTO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),Filterable{

    // 필터링된 DTO를 새로이 만들고 기존의 데이터를 넣는다.
    var dataDTOfilter = ArrayList<dataDTO>()
    init {
        dataDTOfilter = dataDTO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            0 -> {
                var view = LayoutInflater.from(context).inflate(R.layout.item_posting,parent,false)
                CustomViewHolder(view)
            }
            else -> throw RuntimeException("Err")
        }

    }

    inner class CustomViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun getItemCount(): Int {
        return dataDTOfilter.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataDTOfilter[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        //recyclerview data를 연결
        var view = holder.itemView
        view.item_textview_content.text = dataDTOfilter[position].content

    }

    /* Filter */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataDTOfilter = dataDTO
                } else {
                    val resultList = ArrayList<dataDTO>()
                    for (row in dataDTO)
                        //검색되어 나온 값들을 새로운 DTO 배열에 add한다.
                        if (row.content.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                    }
                    dataDTOfilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataDTOfilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataDTOfilter = results?.values as ArrayList<dataDTO>
                notifyDataSetChanged()
            }

        }

    }
}
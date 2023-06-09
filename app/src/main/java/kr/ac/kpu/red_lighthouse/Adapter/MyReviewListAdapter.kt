package kr.ac.kpu.red_lighthouse.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kr.ac.kpu.red_lighthouse.R
import kr.ac.kpu.red_lighthouse.placeReview.PlaceReview

class MyReviewListAdapter(val context: Context, val reviewList: ArrayList<PlaceReview>) : BaseAdapter(){
    @SuppressLint("MissingInflatedId", "ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.my_review_item, null)

        val placeName = view.findViewById<TextView>(R.id.placeName)
        val date = view.findViewById<TextView>(R.id.date)
        val reviewContent = view.findViewById<TextView>(R.id.review_content)

        val review = reviewList[position]
        placeName.text = review.placeName
        date.text = review.dateOfReview
        reviewContent.text = review.review

        return view
    }

    override fun getItem(position: Int): Any {
        return reviewList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return reviewList.size
    }
}
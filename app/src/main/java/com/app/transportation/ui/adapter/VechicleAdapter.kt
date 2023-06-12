package com.app.transportation.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.transportation.R
import com.app.transportation.data.model.VehicleModel.VehicleResponseModel.Vehical


class VechicleAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<VechicleAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var vechicleModelArrayList: ArrayList<Vehical> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mVechicleModelArrayList: ArrayList<Vehical>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_vechicle, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            if (vechicleModelArrayList[position].vehical.carNumber==null){
                tvVechileNo.text = "Vehicle No: "
            }else{
                tvVechileNo.text = "Vehicle No: "+vechicleModelArrayList[position].vehical.carNumber

            }


            if (vechicleModelArrayList[position].carDate==null){
                tvDate.text = "Date : "
            }else{
                tvDate.text = "Date : "+vechicleModelArrayList[position].carDate

            }


            if (vechicleModelArrayList[position].inTime==null){
                tvIntime.text = "In-Time : "
            }else{
                tvIntime.text = "In-Time : "+vechicleModelArrayList[position].inTime

            }


            if (vechicleModelArrayList[position].outTime==null){
                tvOuttime.text = "Out-Time : "

            }else{

                tvOuttime.text = "Out-Time : "+vechicleModelArrayList[position].outTime

            }


            if (vechicleModelArrayList[position].inTimeImg==null && vechicleModelArrayList[position].outTimeImg==null){
                imgAttached.visibility=View.GONE
            }else{
                imgAttached.visibility=View.VISIBLE

            }

//            if (vechicleModelArrayList[position].outTimeImg==null){
//                imgAttached.visibility=View.GONE
//            }else{
//                imgAttached.visibility=View.VISIBLE
//
//            }


            llvechicledeatils.setOnClickListener {

                if (vechicleModelArrayList[position].inTimeImg==null && vechicleModelArrayList[position].outTimeImg==null){
                }else{
                    onItemClickListener.onClick(position, it, vechicleModelArrayList)
                }

//                if (vechicleModelArrayList[position].outTimeImg==null){
//                }else{
//                    onItemClickListener.onClick(position, it, vechicleModelArrayList)
//
//                }

//                onItemClickListener.onClick(position, it, vechicleModelArrayList)


//                val navController = Navigation.findNavController(it)
//                navController.navigate(R.id.nav_orderdetails)
            }


        }
    }

    fun updateData( mVechicleModelArrayList: List<Vehical>) {
        vechicleModelArrayList.clear()
        vechicleModelArrayList = mVechicleModelArrayList as ArrayList<Vehical>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return vechicleModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvVechileNo: TextView
        var tvDate: TextView
        var tvIntime: TextView
        var tvOuttime: TextView
        var imgAttached: ImageView
        var llvechicledeatils: CardView


        init {

            tvVechileNo = itemView.findViewById(R.id.tvVechileNo)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvIntime = itemView.findViewById(R.id.tvIntime)
            tvOuttime = itemView.findViewById(R.id.tvOuttime)
            llvechicledeatils = itemView.findViewById(R.id.llvechicledeatils)
            imgAttached = itemView.findViewById(R.id.imgAttached)
        }
    }
}
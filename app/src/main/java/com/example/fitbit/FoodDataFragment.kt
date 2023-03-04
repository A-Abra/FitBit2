package com.example.fitbit

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodDataFragment : Fragment() {
    private val newFoodActivityRequestCode = 1
    private val fooditems = mutableListOf<DisplayItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvHighestCal = view.findViewById<TextView>(R.id.tvHigh)
        val tvLowestCal = view.findViewById<TextView>(R.id.tvLow)
        val tvAverageCal = view.findViewById<TextView>(R.id.tvAverage)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvFood)
        val adapter = FoodAdapter(requireContext(),fooditems)
        recyclerView?.adapter = adapter

        recyclerView?.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            recyclerView?.addItemDecoration(dividerItemDecoration)
        }
        view?.findViewById<Button>(R.id.btnAdd)?.setOnClickListener{
            val intent = Intent(activity, DetailActivity::class.java)
            startActivityForResult(intent, newFoodActivityRequestCode)
        }

        lifecycleScope.launch {
            (activity?.application as FoodApplication).db.healthDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    DisplayItem(
                        foodName = entity.foodname,
                        foodCal = entity.calories
                    )
                }.also { mappedList ->
                    fooditems.clear()
                    Log.i("FoodFragment", mappedList.toString())
                    fooditems.addAll(mappedList)

                    // find item with highest calories
                    var highest : Int = 0
                    for (calories in fooditems.map{it.foodCal} ){
                        if(highest < calories!!) {
                            highest = calories
                        }
                    }
                    tvHighestCal.text = highest.toString()

                    // find item with lowest calories
                    var lowest : Int = highest
                    for (calories in fooditems.map{it.foodCal} ){
                        if(lowest > calories!!) {
                            lowest = calories
                        }
                    }
                    tvLowestCal.text = lowest.toString()

                    // find average of calories
                    var avgCal : Double = 0.00
                    var total :Double = 0.00
                    for (calories in fooditems.map{it.foodCal}){
                        if (calories != null) {
                            total += calories
                        }
                    }
                    avgCal = total/fooditems.size
                    tvAverageCal.text = avgCal.toString()

                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    //check for when new foods added to update caloric data
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newFoodActivityRequestCode && resultCode == Activity.RESULT_OK) {

            intentData?.let{data ->
                lifecycleScope.launch(Dispatchers.IO) {
                    (activity?.application as FoodApplication).db.healthDao().insertAll(
                        FoodItem(
                            id = 0,
                            foodname = data?.getStringExtra(DetailActivity.EXTRA_FOOD),
                            calories = data?.getStringExtra(DetailActivity.EXTRA_CALORIES)?.toInt()
                        )
                    )
                }
            }
        } else {
            Toast.makeText(
                context,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    companion object {
        const val TAG = "FoodDataFragment"
    }
}
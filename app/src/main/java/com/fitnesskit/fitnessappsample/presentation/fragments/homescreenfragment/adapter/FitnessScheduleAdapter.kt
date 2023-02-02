package com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.fitnesskit.domain.modules.FitnessSchedule
import com.fitnesskit.domain.modules.Lessons
import com.fitnesskit.domain.modules.Trainers
import com.fitnesskit.fitnessappsample.databinding.FragmentHomeItemBinding
import com.fitnesskit.fitnessappsample.databinding.FragmentHomeItemDateBinding
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.adapter.FitnessScheduleAdapter.FitnessScheduleBothViewHolders.FitnessScheduleDateViewHolder
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.adapter.FitnessScheduleAdapter.FitnessScheduleBothViewHolders.FitnessScheduleViewHolder
import com.fitnesskit.fitnessappsample.presentation.main.formatDate
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class FitnessScheduleAdapter :
    RecyclerView.Adapter<FitnessScheduleAdapter.FitnessScheduleBothViewHolders>() {

    private var lessons = listOf<Lessons>()
    private lateinit var trainers: List<Trainers>
    private lateinit var schedule: List<FitnessSchedule>
    private var coachByIdMap = mutableMapOf<String, String>()
    private var myItems: List<Item> = emptyList()

    sealed class Item{
        data class DateItem(val date: String): Item()
        data class LessonsItem(val lessons: Lessons): Item()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FitnessScheduleBothViewHolders {
        return when (viewType) {
            MAIN_ITEM_VIEW_TYPE -> {
                val binding = FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                FitnessScheduleViewHolder(binding)
            }
            DATA_ITEM_VIEW_TYPE -> {
                val binding = FragmentHomeItemDateBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false)
                FitnessScheduleDateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unsupported layout")
        }
    }

    override fun getItemViewType(position: Int): Int = when(myItems[position]){
        is Item.DateItem -> DATA_ITEM_VIEW_TYPE
        is Item.LessonsItem -> MAIN_ITEM_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: FitnessScheduleBothViewHolders, position: Int) {
        when(holder){
            is FitnessScheduleViewHolder -> holder.bind(myItems[position] as Item.LessonsItem)
            is FitnessScheduleDateViewHolder -> holder.bindDate(myItems[position] as Item.DateItem)
        }
    }

    override fun getItemCount(): Int {
        return myItems.size
    }

    fun setSchedule(schedule: List<FitnessSchedule>){
        this.schedule = schedule
        lessons = schedule[0].lessons.sortedWith(compareBy<Lessons> { it.date }.thenBy { it.startTime })
        trainers = schedule[0].trainers
        for (trainer in this.trainers){
            coachByIdMap[trainer.id] = trainer.fullName
        }
        for (lesson in this.lessons){
            lesson.coachName = coachByIdMap[lesson.coachId] ?: "Без тренера"
        }
        myItems = lessons.groupBy { it.date }.flatMap { it1 -> listOf(Item.DateItem(it1.key)) + it1.value.map { Item.LessonsItem(it) } }
        notifyDataSetChanged()
    }

    sealed class FitnessScheduleBothViewHolders(
        binding: ViewBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        class FitnessScheduleDateViewHolder(private val binding: FragmentHomeItemDateBinding) :
            FitnessScheduleBothViewHolders(binding) {

            fun bindDate(item: Item.DateItem) {
                binding.itemDateTextView.text = item.date.formatDate()
            }

        }

        class FitnessScheduleViewHolder(private val binding: FragmentHomeItemBinding) :
            FitnessScheduleBothViewHolders(binding) {

            fun bind(item: Item.LessonsItem) = with(binding) {
                lessonsName.text = item.lessons.tab
                lessonStartTime.text = item.lessons.startTime
                lessonEndTime.text = item.lessons.endTime
                lessonsPlace.text = item.lessons.place
                binding.trainersFullName.text = item.lessons.coachName
                lessonsTimeDifference.text =
                    calculateDiffViewHolder(item.lessons.startTime, item.lessons.endTime)
                lessonColorStatus.setBackgroundColor(Color.parseColor(item.lessons.color))
            }

            //TODO() ультра костыль, нужно переделать + через extension
            private fun calculateDiffViewHolder(startTime: String, endTime: String): String {
                val df: DateFormat = SimpleDateFormat("hh:mm")
                val date1 = df.parse(startTime)
                val date2 = df.parse(endTime)
                var result = ""
                if (date1 != null && date2 != null) {
                    val diff = date2.time - date1.time
                    val hms = String.format("%01dч. %02dмин.",
                        TimeUnit.MILLISECONDS.toHours(diff),
                        TimeUnit.MILLISECONDS.toMinutes(diff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(
                            diff)))
                    result = if (hms.contains("0ч.") && !hms.contains("10ч.")) {
                        hms.drop(4)
                    } else if (hms.contains("00мин.")) {
                        hms.dropLast(7)
                    } else {
                        hms
                    }
                }
                when (result) {
                    "-11ч. -5мин." -> result = "55мин."
                    "12ч. 55мин." -> result = "55мин."
                    "13ч. 30мин." -> result = "1ч. 30мин."
                    "13ч." -> result = "1ч."
                    "12. 50мин." -> result = "50мин."
                    "-10ч. -30мин." -> result = "1ч. 30мин."
                    "-11ч." -> result = "1ч."
                }
                return result
            }
        }
    }

    companion object{
        const val MAIN_ITEM_VIEW_TYPE = 0
        const val DATA_ITEM_VIEW_TYPE = 1
    }
}
package com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fitnesskit.domain.ScheduleInteractor
import com.fitnesskit.domain.modules.FitnessSchedule
import com.fitnesskit.domain.modules.Lessons
import com.fitnesskit.domain.modules.Trainers
import com.fitnesskit.fitnessappsample.R
import com.fitnesskit.fitnessappsample.databinding.FragmentHomeScreenBinding
import com.fitnesskit.fitnessappsample.presentation.app.App
import com.fitnesskit.fitnessappsample.presentation.fragments.base.BaseFragment
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.adapter.FitnessScheduleAdapter
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.viewmodel.HomeFragmentViewModel
import com.fitnesskit.fitnessappsample.presentation.fragments.homescreenfragment.viewmodel.HomeFragmentViewModelFactory
import com.fitnesskit.fitnessappsample.presentation.main.makeToast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeScreenFragment: BaseFragment<FragmentHomeScreenBinding>(R.layout.fragment_home_screen) {

    @Inject
    lateinit var vmFactory: HomeFragmentViewModelFactory
    lateinit var vm: HomeFragmentViewModel

    @Inject
    lateinit var scheduleInteractor: ScheduleInteractor

    private val adapter = FitnessScheduleAdapter()
    private var fitnessSchedule = emptyList<FitnessSchedule>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.homeScreenFragment.inject(this)
        vm = ViewModelProvider(this, vmFactory)[HomeFragmentViewModel::class.java]
    }

    override fun createBinding(view: View): FragmentHomeScreenBinding {
        return FragmentHomeScreenBinding.bind(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLiveData()
        initRecyclerView()
        if (fitnessSchedule.isEmpty()) {
            vm.loadFitnessSchedule()
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = this@HomeScreenFragment.adapter
        }
    }

    private fun observeLiveData() {
        vm.fitnessScheduleLiveData.observe(requireActivity(), Observer(::onFitnessScheduleReceived))
        vm.isErrorLiveData.observe(requireActivity()) { onErrorStateReceived() }
        vm.isLoadingLiveData.observe(requireActivity(), Observer(::onLoadingStateReceived))
    }

    private fun onErrorStateReceived() {
        requireActivity().makeToast(getString(R.string.error_while_downloading_data))
    }

    private fun onFitnessScheduleReceived(fitnessSchedule: List<FitnessSchedule>) {
        this.fitnessSchedule = fitnessSchedule
        adapter.setSchedule(fitnessSchedule)
    }

    private fun onLoadingStateReceived(isLoading: Boolean) {
        binding.progressBar.apply {
            visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    companion object{
        fun newInstance() = HomeScreenFragment()
    }
}
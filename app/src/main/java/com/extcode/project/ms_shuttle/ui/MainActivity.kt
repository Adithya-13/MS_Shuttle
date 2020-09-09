package com.extcode.project.ms_shuttle.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.extcode.project.ms_shuttle.ui.fragment.DatePickerFragment
import com.extcode.project.ms_shuttle.R
import com.extcode.project.ms_shuttle.ui.OrderActivity.Companion.EXTRA_DATA_RESERVATION
import com.extcode.project.ms_shuttle.adapter.SliderAdapter
import com.extcode.project.ms_shuttle.entity.DataImageSlider
import com.extcode.project.ms_shuttle.entity.DataReservation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.destination_layout.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener,
    DatePickerFragment.DialogDateListener {

    private val handler = Handler()
    private val sliderRunnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container.layoutAnimation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_up_anim)

        ArrayAdapter.createFromResource(
            this,
            R.array.pick_up_item,
            android.R.layout.simple_list_item_1
        ).also { arrayAdapter ->
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            pickUpSpinner.adapter = arrayAdapter
        }
        pickUpSpinner.onItemSelectedListener = this

        configImageSlider()

        dateButton.setOnClickListener(this)
        toDataUserActivity.setOnClickListener(this)
        listOrderButton.setOnClickListener(this)
        customerServiceButton.setOnClickListener(this)
        signUpMember.setOnClickListener(this)
        language.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dateButton -> {
                val datePickerFragment =
                    DatePickerFragment()
                datePickerFragment.show(supportFragmentManager, "DatePicker")
            }
            R.id.toDataUserActivity -> {
                when {
                    pickUpSpinner.selectedItemPosition == 0 -> Toast.makeText(
                        this,
                        getString(R.string.please_select_pick_up),
                        Toast.LENGTH_SHORT
                    ).show()
                    dateText.text == getString(R.string.select_date) -> Toast.makeText(
                        this,
                        getString(R.string.please_select_date),
                        Toast.LENGTH_SHORT
                    ).show()
                    timeSpinner.selectedItemPosition == 0 -> Toast.makeText(
                        this,
                        getString(R.string.please_select_time),
                        Toast.LENGTH_SHORT
                    ).show()
                    destinationSpinner.selectedItemPosition == 0 -> Toast.makeText(
                        this,
                        getString(R.string.please_select_destination),
                        Toast.LENGTH_SHORT
                    ).show()
                    else -> {
                        val pickUp = pickUpSpinner.selectedItem.toString()
                        val date = dateText.text.toString().trim()
                        val time = timeSpinner.selectedItem.toString()
                        val destination = destinationSpinner.selectedItem.toString()
                        val bookingCode = Random().nextInt(100000001)
                        val dataReservation =
                            DataReservation(
                                pickUp,
                                date,
                                time,
                                destination,
                                bookingCode
                            )

                        val intent = Intent(this, OrderActivity::class.java)
                        intent.putExtra(EXTRA_DATA_RESERVATION, dataReservation)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                    }
                }
            }
            R.id.listOrderButton ->{
                startActivity(Intent(this, ListOrderActivity::class.java))
                overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
            }
            R.id.customerServiceButton ->{
                try {
                    val text = getString(R.string.whatsapp)
                    val number = "+628111321112"
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$number&text=$text")
                    startActivity(intent)
                    overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, getString(R.string.install_wa), Toast.LENGTH_LONG).show()
                }
            }
            R.id.signUpMember -> {
                try {
                    val signUp = Intent(Intent.ACTION_VIEW)
                    signUp.data = Uri.parse("https://forms.gle/4vDSXP9TZSNhb74R7")
                    startActivity(signUp)
                    overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
                }catch (e: Exception){
                    e.printStackTrace()
                    Toast.makeText(this, getString(R.string.failed_sign_up_member), Toast.LENGTH_SHORT).show()
                }
            }
            R.id.language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                overridePendingTransition(R.anim.fragment_fade_enter,R.anim.fragment_fade_exit)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val destinationAdapter: ArrayAdapter<CharSequence>
        val timeAdapter: ArrayAdapter<CharSequence>
        when (position) {
            0 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_null,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_null,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            1 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_cikijing_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_cikijing_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            2 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_talaga_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_talaga_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            3 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_maja_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_maja_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            4 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_cigasong_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_cigasong_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            5 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_munjul_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_munjul_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            6 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_kadipaten_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_kadipaten_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            7 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_jatinangor_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_jatinangor_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            8 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_pasteur_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_pasteur_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
            9 -> {
                destinationAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.destination_gasibu_item,
                    android.R.layout.simple_list_item_1
                )
                timeAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.time_gasibu_item,
                    android.R.layout.simple_list_item_1
                )
                destinationSpinner.adapter = destinationAdapter
                timeSpinner.adapter = timeAdapter
            }
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        dateText.text = dateFormat.format(calendar.time)
    }

    private fun configImageSlider() {
        val sliderItems = arrayListOf<DataImageSlider>()
        sliderItems.add(DataImageSlider(R.drawable.image_8))
        sliderItems.add(DataImageSlider(R.drawable.image_1))
        sliderItems.add(DataImageSlider(R.drawable.image_2))
        sliderItems.add(DataImageSlider(R.drawable.image_3))
        sliderItems.add(DataImageSlider(R.drawable.image_4))
        sliderItems.add(DataImageSlider(R.drawable.image_5))
        sliderItems.add(DataImageSlider(R.drawable.image_6))
        sliderItems.add(DataImageSlider(R.drawable.image_7))
        viewPager2.apply {
            adapter = SliderAdapter(
                sliderItems,
                viewPager2
            )
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r: Float = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }

        viewPager2.setPageTransformer(compositePageTransformer)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(sliderRunnable)
                handler.postDelayed(sliderRunnable, 3000)
            }
        })

    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(sliderRunnable, 3000)
    }
}
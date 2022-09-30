package com.example.tagtrainermobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.tagtrainermobile.models.User


class LoginFragment : Fragment() {

    val user = User.sigleUser.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar()
        setButtonLoginConfig()
    }

    fun setProgressBar() {
        val progressBar = getView()?.findViewById<ProgressBar>(R.id.progressBar)
        progressBar?.progress = 33
    }

    fun setButtonLoginConfig() {
        val button = getView()?.findViewById<Button>(R.id.enterLoginId)
        button?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                user.isLogged = true
                val paymentFragment = PaymentFragment()
              getFragmentManager()?.beginTransaction()?.add(R.id.fragmentPaymentId, paymentFragment)?.commit()
            }
        })
    }
}
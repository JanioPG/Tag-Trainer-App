package com.example.tagtrainermobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tagtrainermobile.models.User
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.analytics.ktx.logEvent


class PaymentFragment : Fragment(), CartActivity.setRadioButtonsConfig {

    val user = User.sigleUser.instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setProgressBar()
        setRadioButtonsConfig()
    }

    fun setProgressBar() {
        val progressBar = getView()?.findViewById<ProgressBar>(R.id.progressBar)
        if(user.isLogged) {
            progressBar?.progress = 66
        } else {
            progressBar?.progress = 33
        }
    }

        override fun setRadioButtonsConfig() {
            val btnView = getView()?.findViewById<RadioButton>(R.id.bankSlipId)
            val txtDados  = getView()?.findViewById<TextView>(R.id.dataConfId)
            val inputName = getView()?.findViewById<TextInputLayout>(R.id.textInputLayout)
            val inputCpf =  getView()?.findViewById<TextInputLayout>(R.id.cpfInputLayout)
            val buttonFinish = getView()?.findViewById<Button>(R.id.finalizarCompraBkslp)
            if (btnView != null) {
                btnView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(view: View?) {
                        if (btnView.isChecked) {
                            txtDados?.visibility = View.VISIBLE
                            inputName?.visibility = View.VISIBLE
                            inputCpf?.visibility = View.VISIBLE
                            buttonFinish?.visibility = View.VISIBLE

                            FirebaseEvent.firebasePaymentInfo("Boleto")

                            buttonFinish?.setOnClickListener {
                                val transactionFragment = TransactionFragment()
                                getFragmentManager()?.beginTransaction()?.add(R.id.fragmentPaymentId, transactionFragment)?.commit()
//                                val intent = Intent(context, PurchaseActivity::class.java)
//                                startActivity(intent)
//                                Toast.makeText(getActivity(), "oiioi", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
}

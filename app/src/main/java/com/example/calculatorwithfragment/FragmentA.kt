package com.example.calculatorwithfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.clearFragmentResult
import androidx.fragment.app.setFragmentResultListener


class FragmentA : Fragment() ,View.OnClickListener{
    private lateinit var buttonAdd: Button
    private lateinit var buttonSub: Button
    private lateinit var buttonMultiply: Button
    private lateinit var buttonDivide: Button
    private lateinit var textView: TextView
    private lateinit var buttonReset: Button
    private var Result: String? = null
    private var state: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_a, container, false)
        if (savedInstanceState != null) {
            state = savedInstanceState.getInt("state")
            Result = savedInstanceState.getString("result")
        }
        buttonAdd = inflate.findViewById(R.id.buttonAdd)
        buttonSub = inflate.findViewById(R.id.buttonSub)
        buttonMultiply = inflate.findViewById(R.id.buttonMultiply)
        buttonDivide = inflate.findViewById(R.id.buttonDivide)
        textView = inflate.findViewById(R.id.textViewresult)
        buttonReset = inflate.findViewById(R.id.buttonReset)
        buttonAdd.setOnClickListener(this)
        buttonSub.setOnClickListener (this)
        buttonMultiply.setOnClickListener (this)
        buttonDivide.setOnClickListener (this)
        buttonReset.setOnClickListener {
            clearFragmentResult("KEY")
            Result = null
            onReset()
        }
        if (state == 0) {
            onReset()
        } else {
            textView.text = Result
            onResult()
        }
        return inflate
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("state", state)
        outState.putString("result", Result)
    }

    private fun goToFragB(operationName: String) {

        val fragmentTwo = FragmentB()
        val args = Bundle()
        args.putString("operation", operationName)
        fragmentTwo.arguments = args
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragmentholder, fragmentTwo, "FragB")
        ?.addToBackStack("FragA")
        ?.commit()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.supportFragmentManager?.setFragmentResultListener("KEY",viewLifecycleOwner) { _, result ->
            result.getString("RESULT")?.let { stringMine ->
                textView.text = stringMine
                Result = stringMine
                onResult()
            }
        }
    }

    private fun onResult() {
        state = 1
        textView.visibility = View.VISIBLE
        buttonReset.visibility = View.VISIBLE
        buttonAdd.visibility = View.GONE
        buttonSub.visibility = View.GONE
        buttonMultiply.visibility = View.GONE
        buttonDivide.visibility = View.GONE
    }

    private fun onReset() {
        state = 0
        textView.visibility = View.GONE
        buttonReset.visibility = View.GONE
        buttonAdd.visibility = View.VISIBLE
        buttonSub.visibility = View.VISIBLE
        buttonMultiply.visibility = View.VISIBLE
        buttonDivide.visibility = View.VISIBLE
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.buttonAdd -> goToFragB(buttonAdd.text.toString())
            R.id.buttonSub -> goToFragB(buttonSub.text.toString())
            R.id.buttonMultiply -> goToFragB(buttonMultiply.text.toString())
            R.id.buttonDivide -> goToFragB(buttonDivide.text.toString())

        }
    }

}

package com.example.calculatorwithfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf

class FragmentB : Fragment() {
    private lateinit var result: String
    private lateinit var operationName: String
    private lateinit var num1: EditText
    private lateinit var num2: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflate = inflater.inflate(R.layout.fragment_b, container, false)
        val buttonResult: Button = inflate.findViewById(R.id.buttonResult)
        num1 = inflate.findViewById(R.id.editTextNumber)
        num2 = inflate.findViewById(R.id.editTextNumber2)
        operationName = arguments?.getString("operation").toString()
        if (savedInstanceState != null) {
            val fm = activity?.supportFragmentManager
            val previousFragmentB = fm?.findFragmentByTag("FragB")
            if (previousFragmentB != null) {
                fm.popBackStack()
                val transaction1 = fm.beginTransaction()
                transaction1.remove(previousFragmentB).commit()
                val fragmentTwo = FragmentB()
                val bundle = previousFragmentB.arguments
                bundle?.putString("num1", savedInstanceState.getString("num1"))
                bundle?.putString("num2", savedInstanceState.getString("num2"))
                fragmentTwo.arguments = bundle
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentholder, fragmentTwo, "FragB")
                    ?.addToBackStack("FragB")
                    ?.commit()
            }
        }
        num1.text.append(arguments?.getString("num1").toString())
        num2.text.append(arguments?.getString("num2").toString())
        buttonResult.text = operationName
        buttonResult.setOnClickListener {
            val actionResult = operation(
                num1.text.toString().toDouble(),
                num2.text.toString().toDouble(),
                buttonResult.text.toString()
            )
            result =
                "Action :  " + operationName + "\nInput1 :  " + num1.text + "\nInput2 :  " + num2.text + "\nResult :  " + actionResult
            if (actionResult != null)
                navigateToPreviousFragment()
        }
        return inflate
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("operation", operationName)
        if (num1.text != null)
            outState.putString("num1", num1.text.toString())
        if (num2.text != null)
            outState.putString("num2", num2.text.toString())
    }

    private fun operation(num1: Double?, num2: Double?, action: String): Double? {
        return if (num1 != null && num2 != null) {
            when (action) {
                "ADDITION" -> num1 + num2
                "SUB" -> num1 - num2
                "MULTIPLY" -> num1 * num2
                "DIVIDE" -> num1 / num2
                else -> {
                    return null
                }
            }
        } else {
            Toast.makeText(activity, "enter all values", Toast.LENGTH_LONG).show()
            null
        }
    }

    private fun navigateToPreviousFragment() {
        activity?.supportFragmentManager?.setFragmentResult("KEY", bundleOf("RESULT" to result))
        fragmentManager?.popBackStackImmediate()
    }
}
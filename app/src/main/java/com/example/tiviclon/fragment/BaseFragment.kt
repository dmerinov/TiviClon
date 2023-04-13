package com.example.tiviclon.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){
    override fun getContext() = activity
}
package com.ida.mymovie.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ida.mymovie.R
import com.ida.mymovie.databinding.FragmentLoginBinding
import com.ida.mymovie.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private var mDb : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val LOGINUSER = "login_username"
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = UserDatabase.getInstance(requireContext())

        //SharedPreferences
        val preferences = requireContext().getSharedPreferences(LOGINUSER, Context.MODE_PRIVATE)

        val username = preferences.getString(USERNAME, "default_username")
        if (username != "default_username") {
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }

        binding.btnRegisterText.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            when {
                binding.etUsername.text.isNullOrEmpty() -> {
                    binding.etUsername.error = "Username belum diisi"
                }
                binding.etPassword.text.isNullOrEmpty() -> {
                    binding.etPassword.error = "Password belum diisi"
                } else -> {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                lifecycleScope.launch(Dispatchers.IO) {
                    val result = mDb?.userDao()?.loginUser(username, password)
                    runBlocking(Dispatchers.Main) {
                        if (result == true) {
                            val editor : SharedPreferences.Editor = preferences.edit()
                            editor.putString(USERNAME, binding.etUsername.text.toString())
                            editor.putString(PASSWORD, binding.etPassword.text.toString())
                            editor.apply()
                            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        } else {
                            val snackbar = Snackbar.make(it,"Login gagal", Snackbar.LENGTH_INDEFINITE)
                            snackbar.setAction("Oke") {
                                snackbar.dismiss()
                            }
                            snackbar.show()
                        }
                    }
                }
            }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
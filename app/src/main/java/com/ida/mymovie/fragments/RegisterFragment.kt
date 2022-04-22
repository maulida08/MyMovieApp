package com.ida.mymovie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ida.mymovie.R
import com.ida.mymovie.databinding.FragmentRegisterBinding
import com.ida.mymovie.room.User
import com.ida.mymovie.room.UserDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var mDb : UserDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDb = UserDatabase.getInstance(requireContext())

        binding.btnRegister.setOnClickListener{
            when {
                binding.etUsername.text.toString().isEmpty() -> {
                    binding.etUsername.error = "Username belum diisi"
                    Toast.makeText(requireContext(), "Username belum diisi", Toast.LENGTH_SHORT).show()
                }
                binding.etEmail.toString().isEmpty() -> {
                    binding.etEmail.error = "Email belum diisi"
                    Toast.makeText(requireContext(), "Email belum diisi", Toast.LENGTH_SHORT).show()
                }
                binding.etPassword.toString().isEmpty() -> {
                    binding.etPassword.error = "Password belum diisi"
                    Toast.makeText(requireContext(), "Password belum diisi", Toast.LENGTH_SHORT).show()
                }
                binding.etConfirmPassword.toString().isEmpty() -> {
                    binding.etConfirmPassword.error = "Password Confirm belum diisi"
                    Toast.makeText(requireContext(), "Password Confirm belum diisi", Toast.LENGTH_SHORT).show()
                }
                binding.etConfirmPassword.text.toString().lowercase() != binding.etConfirmPassword.text.toString().lowercase() -> {
                    binding.etConfirmPassword.error = "Password tidak sama, harap ulangi kembali"
                    Toast.makeText(requireContext(), "Password tidak sama, harap ulangi kembali", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = User(
                        null,
                        binding.etUsername.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                    lifecycleScope.launch(Dispatchers.IO) {
                        val result = mDb?.userDao()?.insertUser(user)
                        runBlocking(Dispatchers.Main){
                            if (result != 0.toLong()){
                                Toast.makeText(activity, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                            } else {
                                Toast.makeText(activity, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
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
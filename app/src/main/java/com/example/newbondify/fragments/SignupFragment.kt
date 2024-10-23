package com.example.newbondify.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newbondify.R
import com.example.newbondify.databinding.FragmentSignupBinding
import com.example.newbondify.classes.UserData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        databaseReference = FirebaseDatabase.getInstance().reference.child("users")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupButton.setOnClickListener {
            val email = binding.emailsign.text.toString().trim()
            val password = binding.passwordsign.text.toString().trim()
            val username = binding.usernamesign.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                if (password == binding.repeatpasswordsign.text.toString()) {
                    saveUserToDatabase(username, email, password)
                } else {
                    binding.repeatpasswordsign.error = "Passwords do not match"
                }
            } else {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToDatabase(username: String, email: String, password: String) {
        val userId = databaseReference.push().key
        // make firebase Generate a unique key for each user

        if (userId != null) {
            val user = UserData(id = userId.hashCode(), email = email, password = password, username = username)

            databaseReference.child(userId).setValue(user)
                /*[search and set value]
                databaseReference.child(userId):
              This navigates to the specific user node under the users node, using the userId as the key. Essentially, the data will be stored at the location: /users/userId.
              setValue(user):
              This saves the UserData object to the database at the userId node. The Firebase Realtime Database automatically stores the object as JSON format.
              */
                .addOnCompleteListener { task ->
                    //After attempting to save the data, we add a listener to know whether the operation was successful or not.
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Signup Successful", Toast.LENGTH_SHORT).show()
                        // Navigate to LoginFragment after successful signup
                        findNavController().navigate(R.id.loginFragment)
                    } else {
                        Toast.makeText(requireContext(), "Failed to store user data", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

package com.petplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class PetProfileFragment : Fragment() {
    private lateinit var nameValue: TextView
    private lateinit var breedValue: TextView
    private lateinit var ageValue: TextView
    private lateinit var weightValue: TextView
    private lateinit var personalityValue: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pet_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameValue = view.findViewById(R.id.petNameValue)
        breedValue = view.findViewById(R.id.petBreedValue)
        ageValue = view.findViewById(R.id.petAgeValue)
        weightValue = view.findViewById(R.id.petWeightValue)
        personalityValue = view.findViewById(R.id.petPersonalityValue)

        viewLifecycleOwner.lifecycleScope.launch {
            LocalDataRepository.initialize(requireContext())
            val pet = LocalDataRepository.getPet()
            if (pet != null) {
                nameValue.text = pet.name
                breedValue.text = pet.breed
                ageValue.text = pet.age
                weightValue.text = pet.weight
                personalityValue.text = pet.personality
            }
        }
    }
}

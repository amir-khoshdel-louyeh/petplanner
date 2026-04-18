package com.petplanner

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class OnboardingFragment : Fragment() {
    private lateinit var stepOneContainer: View
    private lateinit var stepTwoContainer: View
    private lateinit var onboardingTitle: TextView
    private lateinit var onboardingSubtitle: TextView
    private lateinit var stepIndicator: TextView
    private lateinit var petNameInput: EditText
    private lateinit var petBreedInput: EditText
    private lateinit var petTypeRadioGroup: RadioGroup
    private lateinit var petAgeInput: EditText
    private lateinit var petWeightInput: EditText
    private lateinit var choosePhotoButton: Button
    private lateinit var petPhotoPreview: ImageView
    private lateinit var languageSpinner: Spinner
    private lateinit var remindersSwitch: Switch
    private lateinit var userNameInput: EditText
    private lateinit var userAgeInput: EditText
    private lateinit var userGenderRadioGroup: RadioGroup
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var finishButton: Button

    private var petPhotoUri: Uri? = null
    private var currentStep = 1

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            petPhotoUri = uri
            petPhotoPreview.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupLanguageSpinner()
        showStep(1)

        choosePhotoButton.setOnClickListener {
            imagePicker.launch("image/*")
        }

        nextButton.setOnClickListener {
            if (validateStepOne()) {
                showStep(2)
            }
        }

        backButton.setOnClickListener {
            showStep(1)
        }

        finishButton.setOnClickListener {
            if (validateStepTwo()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val success = saveOnboardingData(requireContext())
                    if (success && activity is MainActivity) {
                        (activity as MainActivity).completeOnboarding()
                    }
                }
            }
        }
    }

    private fun bindViews(view: View) {
        stepOneContainer = view.findViewById(R.id.stepOneContainer)
        stepTwoContainer = view.findViewById(R.id.stepTwoContainer)
        onboardingTitle = view.findViewById(R.id.onboardingTitle)
        onboardingSubtitle = view.findViewById(R.id.onboardingSubtitle)
        stepIndicator = view.findViewById(R.id.stepIndicator)
        petNameInput = view.findViewById(R.id.petNameInput)
        petBreedInput = view.findViewById(R.id.petBreedInput)
        petTypeRadioGroup = view.findViewById(R.id.petTypeRadioGroup)
        petAgeInput = view.findViewById(R.id.petAgeInput)
        petWeightInput = view.findViewById(R.id.petWeightInput)
        choosePhotoButton = view.findViewById(R.id.choosePhotoButton)
        petPhotoPreview = view.findViewById(R.id.petPhotoPreview)
        languageSpinner = view.findViewById(R.id.languageSpinner)
        remindersSwitch = view.findViewById(R.id.remindersSwitch)
        userNameInput = view.findViewById(R.id.userNameInput)
        userAgeInput = view.findViewById(R.id.userAgeInput)
        userGenderRadioGroup = view.findViewById(R.id.userGenderRadioGroup)
        nextButton = view.findViewById(R.id.nextButton)
        backButton = view.findViewById(R.id.backButton)
        finishButton = view.findViewById(R.id.finishButton)
    }

    private fun setupLanguageSpinner() {
        val languages = resources.getStringArray(R.array.language_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.adapter = adapter
    }

    private fun showStep(step: Int) {
        currentStep = step
        stepTwoContainer.visibility = if (step == 2) View.VISIBLE else View.GONE
        stepOneContainer.visibility = if (step == 1) View.VISIBLE else View.GONE
        onboardingSubtitle.visibility = if (step == 1) View.VISIBLE else View.GONE
        backButton.visibility = if (step == 2) View.VISIBLE else View.GONE
        nextButton.visibility = if (step == 1) View.VISIBLE else View.GONE
        finishButton.visibility = if (step == 2) View.VISIBLE else View.GONE
        stepIndicator.text = if (step == 1) getString(R.string.onboarding_step_one) else getString(R.string.onboarding_step_two)
        onboardingTitle.text = if (step == 2) getString(R.string.onboarding_title_pet_profile) else getString(R.string.onboarding_title)
    }

    private fun validateStepOne(): Boolean {
        if (userNameInput.text.isNullOrBlank()) {
            showToast("Please enter your name")
            return false
        }
        val ageText = userAgeInput.text.toString().trim()
        val ageValue = ageText.toIntOrNull()
        if (ageText.isEmpty() || ageValue == null || ageValue <= 0) {
            showToast("Please enter a valid numeric age")
            return false
        }
        if (userGenderRadioGroup.checkedRadioButtonId == -1) {
            showToast("Please select your gender")
            return false
        }
        return true
    }

    private fun validateStepTwo(): Boolean {
        if (petNameInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's name")
            return false
        }
        if (petBreedInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's breed")
            return false
        }
        val ageText = petAgeInput.text.toString().trim()
        val ageValue = ageText.toIntOrNull()
        if (ageText.isEmpty() || ageValue == null || ageValue <= 0) {
            showToast("Please enter a valid numeric age")
            return false
        }
        val weightText = petWeightInput.text.toString().trim()
        val weightValue = weightText.toFloatOrNull()
        if (weightText.isEmpty() || weightValue == null || weightValue <= 0f) {
            showToast("Please enter a valid numeric weight")
            return false
        }
        return true
    }

    private suspend fun saveOnboardingData(context: Context): Boolean {
        val name = petNameInput.text.toString().trim()
        val breed = petBreedInput.text.toString().trim()
        val age = petAgeInput.text.toString().trim()
        val weight = petWeightInput.text.toString().trim()
        val language = languageSpinner.selectedItem.toString()
        val petType = if (petTypeRadioGroup.checkedRadioButtonId == R.id.petTypeCatRadio) "Cat" else "Dog"

        val userName = userNameInput.text.toString().trim()
        val userAge = userAgeInput.text.toString().trim()
        val userGender = when (userGenderRadioGroup.checkedRadioButtonId) {
            R.id.userGenderFemaleRadio -> "Female"
            R.id.userGenderOtherRadio -> "Other"
            else -> "Male"
        }

        val summary = "$breed · $age · $petType"
        val personality = "Personality: playful, curious, loves daily routines"
        val pet = Pet(
            name = name,
            breed = breed,
            age = age,
            summary = summary,
            personality = personality,
            mood = "Happy",
            weight = weight
        )

        OnboardingPreferences.savePetProfile(context, name, breed, age, weight, petPhotoUri?.toString())
        OnboardingPreferences.saveUserProfile(context, userName, userAge, userGender)
        OnboardingPreferences.saveLanguage(context, language)
        OnboardingPreferences.setOnboardingComplete(context, true)
        LocalDataRepository.savePetProfile(pet)
        LocalDataRepository.seedInitialDataIfNeeded()

        showToast("Setup complete! Welcome to PetPlanner.")
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

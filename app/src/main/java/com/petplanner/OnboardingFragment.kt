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

class OnboardingFragment : Fragment() {
    private lateinit var stepOneContainer: View
    private lateinit var stepTwoContainer: View
    private lateinit var stepIndicator: TextView
    private lateinit var petNameInput: EditText
    private lateinit var petBreedInput: EditText
    private lateinit var petAgeInput: EditText
    private lateinit var petWeightInput: EditText
    private lateinit var choosePhotoButton: Button
    private lateinit var petPhotoPreview: ImageView
    private lateinit var languageSpinner: Spinner
    private lateinit var remindersSwitch: Switch
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
            if (saveOnboardingData(requireContext())) {
                if (activity is MainActivity) {
                    (activity as MainActivity).completeOnboarding()
                }
            }
        }
    }

    private fun bindViews(view: View) {
        stepOneContainer = view.findViewById(R.id.stepOneContainer)
        stepTwoContainer = view.findViewById(R.id.stepTwoContainer)
        stepIndicator = view.findViewById(R.id.stepIndicator)
        petNameInput = view.findViewById(R.id.petNameInput)
        petBreedInput = view.findViewById(R.id.petBreedInput)
        petAgeInput = view.findViewById(R.id.petAgeInput)
        petWeightInput = view.findViewById(R.id.petWeightInput)
        choosePhotoButton = view.findViewById(R.id.choosePhotoButton)
        petPhotoPreview = view.findViewById(R.id.petPhotoPreview)
        languageSpinner = view.findViewById(R.id.languageSpinner)
        remindersSwitch = view.findViewById(R.id.remindersSwitch)
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
        stepOneContainer.visibility = if (step == 1) View.VISIBLE else View.GONE
        stepTwoContainer.visibility = if (step == 2) View.VISIBLE else View.GONE
        backButton.visibility = if (step == 1) View.GONE else View.VISIBLE
        nextButton.visibility = if (step == 1) View.VISIBLE else View.GONE
        finishButton.visibility = if (step == 2) View.VISIBLE else View.GONE
        stepIndicator.text = if (step == 1) getString(R.string.onboarding_step_one) else getString(R.string.onboarding_step_two)
    }

    private fun validateStepOne(): Boolean {
        if (petNameInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's name")
            return false
        }
        if (petBreedInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's breed")
            return false
        }
        if (petAgeInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's age")
            return false
        }
        if (petWeightInput.text.isNullOrBlank()) {
            showToast("Please enter your pet's weight")
            return false
        }
        return true
    }

    private fun saveOnboardingData(context: Context): Boolean {
        val name = petNameInput.text.toString().trim()
        val breed = petBreedInput.text.toString().trim()
        val age = petAgeInput.text.toString().trim()
        val weight = petWeightInput.text.toString().trim()
        val language = languageSpinner.selectedItem.toString()

        OnboardingPreferences.savePetProfile(context, name, breed, age, weight, petPhotoUri?.toString())
        OnboardingPreferences.saveLanguage(context, language)
        OnboardingPreferences.setOnboardingComplete(context, true)
        LocalDataRepository.loadSavedPet(context)

        showToast("Setup complete! Welcome to PetPlanner.")
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}

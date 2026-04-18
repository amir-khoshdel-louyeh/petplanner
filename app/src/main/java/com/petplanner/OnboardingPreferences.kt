package com.petplanner

import android.content.Context

object OnboardingPreferences {
    private const val PREFS_NAME = "petplanner_prefs"
    private const val KEY_ONBOARDING_COMPLETE = "pref_onboarding_complete"
    private const val KEY_PET_NAME = "pref_pet_name"
    private const val KEY_PET_BREED = "pref_pet_breed"
    private const val KEY_PET_AGE = "pref_pet_age"
    private const val KEY_PET_WEIGHT = "pref_pet_weight"
    private const val KEY_PET_PHOTO_URI = "pref_pet_photo_uri"
    private const val KEY_LANGUAGE = "pref_language"
    private const val KEY_USER_NAME = "pref_user_name"
    private const val KEY_USER_AGE = "pref_user_age"
    private const val KEY_USER_GENDER = "pref_user_gender"

    private fun sharedPreferences(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isOnboardingComplete(context: Context): Boolean {
        return sharedPreferences(context).getBoolean(KEY_ONBOARDING_COMPLETE, false)
    }

    fun setOnboardingComplete(context: Context, complete: Boolean) {
        sharedPreferences(context).edit().putBoolean(KEY_ONBOARDING_COMPLETE, complete).apply()
    }

    fun savePetProfile(
        context: Context,
        name: String,
        breed: String,
        age: String,
        weight: String,
        photoUri: String?
    ) {
        sharedPreferences(context).edit()
            .putString(KEY_PET_NAME, name)
            .putString(KEY_PET_BREED, breed)
            .putString(KEY_PET_AGE, age)
            .putString(KEY_PET_WEIGHT, weight)
            .putString(KEY_PET_PHOTO_URI, photoUri)
            .apply()
    }

    fun loadPetProfile(context: Context): Pet? {
        val prefs = sharedPreferences(context)
        val name = prefs.getString(KEY_PET_NAME, null)
        val breed = prefs.getString(KEY_PET_BREED, null)
        val age = prefs.getString(KEY_PET_AGE, null)
        val weight = prefs.getString(KEY_PET_WEIGHT, null)

        if (name.isNullOrBlank() || breed.isNullOrBlank() || age.isNullOrBlank() || weight.isNullOrBlank()) {
            return null
        }

        val summary = "$breed · $age · Pet"
        val personality = "Personality: playful, curious, loves daily routines"
        return Pet(
            name = name,
            breed = breed,
            age = age,
            summary = summary,
            personality = personality,
            mood = "Happy",
            weight = weight
        )
    }

    fun getPetPhotoUri(context: Context): String? {
        return sharedPreferences(context).getString(KEY_PET_PHOTO_URI, null)
    }

    fun saveLanguage(context: Context, language: String) {
        sharedPreferences(context).edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getLanguage(context: Context): String {
        return sharedPreferences(context).getString(KEY_LANGUAGE, "English") ?: "English"
    }

    fun saveUserProfile(context: Context, name: String, age: String, gender: String) {
        sharedPreferences(context).edit()
            .putString(KEY_USER_NAME, name)
            .putString(KEY_USER_AGE, age)
            .putString(KEY_USER_GENDER, gender)
            .apply()
    }

    fun getUserName(context: Context): String? {
        return sharedPreferences(context).getString(KEY_USER_NAME, null)
    }

    fun getUserAge(context: Context): String? {
        return sharedPreferences(context).getString(KEY_USER_AGE, null)
    }

    fun getUserGender(context: Context): String? {
        return sharedPreferences(context).getString(KEY_USER_GENDER, null)
    }
}

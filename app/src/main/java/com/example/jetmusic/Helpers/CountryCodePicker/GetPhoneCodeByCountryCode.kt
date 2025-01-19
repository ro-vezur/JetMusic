package com.example.jetmusic.Helpers.CountryCodePicker

fun getPhoneCodeByCountryCode(countryCode: String): String {
    return countryCodeToPhoneCode[countryCode]?: "00"
}
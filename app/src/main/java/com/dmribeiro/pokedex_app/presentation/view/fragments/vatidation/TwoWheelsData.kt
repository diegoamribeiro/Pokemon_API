package com.dmribeiro.pokedex_app.presentation.view.fragments.vatidation

import java.io.Serializable
import java.util.*

data class TwoWheelsData(
    var fullName: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var otherName: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var birthday: Triple<Boolean, Date?, String?> = Triple(false, null, null),
    var phone: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var email: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var workCityId: Triple<Boolean, Int?, String?> = Triple(false, null, null),
    var cnpj: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var cpf: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var rg: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var cnh: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var cnhExpiration: Triple<Boolean, Date?, String?> = Triple(false, null, null),
    var renavam: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var cnhIsRegulated: Triple<Boolean, Boolean, String?> = Triple(false, false, null),
    var renavamIsRegulated: Triple<Boolean, Boolean, String?> = Triple(false, false, null),
    var vehiclePlate: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var password: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var passwordConfirm: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var isAgreedToTheTerms: Triple<Boolean, Boolean, String?> = Triple(false, false, null),
    var motherName: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var cep: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var street: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var number: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var complement: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var neighborhood: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var city: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var state: Triple<Boolean, String?, String?> = Triple(false, null, null),
    var isCourierRegistered: Boolean = false,
    var isCourierCompleteRegistered: Boolean = false
): Serializable
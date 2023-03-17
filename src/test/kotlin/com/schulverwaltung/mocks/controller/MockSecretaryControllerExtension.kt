package com.schulverwaltung.mocks.controller

import com.schulverwaltung.controller.SecretaryController
import com.schulverwaltung.mocks.repository.MockSecretaryRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IPasswordHasher

class MockSecretaryControllerExtension(
    private val _unitOfWork: IUnitOfWork,
    passwordHasher: IPasswordHasher
) : SecretaryController(_unitOfWork, passwordHasher) {
    fun getAutoIncrementResetCount(): Int =
        (_unitOfWork.secretaryRepository as MockSecretaryRepository).autoIncrementResetCount
}
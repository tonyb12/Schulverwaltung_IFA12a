package com.schulverwaltung.mocks.controller

import com.schulverwaltung.controller.SecretarySecretController
import com.schulverwaltung.mocks.repository.MockSecretarySecretRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

class MockSecretarySecretControllerExtension(private val _unitOfWork: IUnitOfWork) :
    SecretarySecretController(_unitOfWork) {
    fun getAutoIncrementResetCount(): Int =
        (_unitOfWork.secretarySecretRepository as MockSecretarySecretRepository).autoIncrementResetCount
}
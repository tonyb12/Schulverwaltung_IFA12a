package com.schulverwaltung.mocks.controller

import com.schulverwaltung.controller.StudentSecretController
import com.schulverwaltung.mocks.repository.MockStudentSecretRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork

class MockStudentSecretControllerExtension(private val _unitOfWork: IUnitOfWork) :
    StudentSecretController(_unitOfWork) {
    fun getAutoIncrementResetCount(): Int =
        (_unitOfWork.studentSecretRepository as MockStudentSecretRepository).autoIncrementResetCount
}
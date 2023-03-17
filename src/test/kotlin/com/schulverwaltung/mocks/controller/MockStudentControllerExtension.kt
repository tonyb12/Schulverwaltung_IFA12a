package com.schulverwaltung.mocks.controller

import com.schulverwaltung.controller.StudentController
import com.schulverwaltung.mocks.repository.MockStudentRepository
import com.schulverwaltung.unitofwork.interfaces.IUnitOfWork
import com.schulverwaltung.utils.interfaces.IBirthdayParser
import com.schulverwaltung.utils.interfaces.IPasswordHasher
import com.schulverwaltung.utils.interfaces.IUserNameGenerator

class MockStudentControllerExtension(
    private val _unitOfWork: IUnitOfWork,
    passwordHasher: IPasswordHasher,
    userNameGenerator: IUserNameGenerator,
    birthdayParser: IBirthdayParser
) : StudentController(_unitOfWork, passwordHasher, userNameGenerator, birthdayParser) {
    fun getAutoIncrementResetCount(): Int =
        (_unitOfWork.studentRepository as MockStudentRepository).autoIncrementResetCount
}
package com.bsrakdg.shoppingapp

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * Some functions on the view model use coroutine scope (Suspend call uses Main Dispatchers)
 * like insertShoppingItemIntoDb, deleteShoppingItem .
 *
 * When we run tests on the test package we get an error that "Module with the Main dispatcher had failed to initialize.
 * For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used"
 *
 * Test package hasn't Main dispatchers (jvm). But android test package has it.
 *
 * So we need to define our own junit rule : MainCoroutineRule
 */

@ExperimentalCoroutinesApi
class MainCoroutineRule
constructor(
    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}
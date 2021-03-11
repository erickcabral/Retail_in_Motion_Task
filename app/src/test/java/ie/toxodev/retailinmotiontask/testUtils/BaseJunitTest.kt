package ie.toxodev.retailinmotiontask.testUtils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
open class BaseJunitTest {

    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    @Test
    fun notNull(){
        assertNotNull(mainCoroutineScopeRule)
    }
}
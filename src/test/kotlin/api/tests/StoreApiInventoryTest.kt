package api.tests

import api.client.StoreApiClient
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class StoreApiInventoryTest {

    private val client = StoreApiClient()

    @Test
    fun `should return store inventory`() = runBlocking {
        val inventory = client.getInventory()
        assertTrue(inventory.isNotEmpty(), "Expected non-empty inventory")
    }
}

package api.tests

object OrderQuantityValidator {

    const val MIN_QUANTITY = 1
    const val MAX_QUANTITY = 100

    fun isValid(quantity: Int): Boolean = quantity in MIN_QUANTITY..MAX_QUANTITY

    fun requireValid(quantity: Int): Int {
        require(isValid(quantity)) {
            "quantity must be between $MIN_QUANTITY and $MAX_QUANTITY, got $quantity"
        }
        return quantity
    }
}

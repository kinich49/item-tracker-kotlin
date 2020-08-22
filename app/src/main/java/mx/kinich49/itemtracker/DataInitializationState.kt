package mx.kinich49.itemtracker

sealed class DataInitializationState
object Initialized : DataInitializationState()
class NotInitialized(val error: String) : DataInitializationState()
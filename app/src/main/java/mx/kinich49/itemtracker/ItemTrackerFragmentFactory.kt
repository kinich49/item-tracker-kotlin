package mx.kinich49.itemtracker

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import mx.kinich49.itemtracker.shoppingList.ShoppingListFactory
import mx.kinich49.itemtracker.shoppingList.ShoppingListFragment

class ItemTrackerFragmentFactory(private val shoppingListFactory: ShoppingListFactory) :
    FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ShoppingListFragment::class.java.name ->
                ShoppingListFragment(shoppingListFactory)

            else -> super.instantiate(classLoader, className)
        }
    }
}
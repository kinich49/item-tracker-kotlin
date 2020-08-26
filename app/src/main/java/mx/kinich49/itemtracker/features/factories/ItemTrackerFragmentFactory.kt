package mx.kinich49.itemtracker.features.factories

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import mx.kinich49.itemtracker.features.home.HomeFragment
import mx.kinich49.itemtracker.features.shoppingList.ShoppingListFragment

class ItemTrackerFragmentFactory(private val viewModelFactory: ItemTrackerViewModelFactory) :
    FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ShoppingListFragment::class.java.name ->
                ShoppingListFragment(viewModelFactory)
            HomeFragment::class.java.name ->
                HomeFragment(viewModelFactory)
            else -> super.instantiate(classLoader, className)
        }
    }
}
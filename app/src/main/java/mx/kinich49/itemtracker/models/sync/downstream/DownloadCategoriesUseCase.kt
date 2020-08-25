package mx.kinich49.itemtracker.models.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.models.database.Category
import mx.kinich49.itemtracker.models.database.daos.CategoryDao
import mx.kinich49.itemtracker.remote.CategoryService

class DownloadCategoriesUseCase(
    private val categoryService: CategoryService,
    private val categoryDao: CategoryDao
) {

    fun execute(): Completable {
        return downloadCategories()
            .flatMapCompletable { categories ->
                Completable.defer {
                    persistCategories(categories)
                }
            }
    }

    private fun downloadCategories(): Single<List<Category>> {
        return categoryService.getCategories()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun persistCategories(categories: List<Category>): Completable {
        return Completable.create { emitter ->
            categories.toTypedArray()
                .let {
                    categoryDao.insert(*it)
                }
            emitter.onComplete()
        }
    }

}
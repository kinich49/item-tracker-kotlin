package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.database.models.Category
import mx.kinich49.itemtracker.entities.database.daos.CategoryDao
import mx.kinich49.itemtracker.entities.apis.services.CategoryService

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
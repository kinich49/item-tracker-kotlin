package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable
import io.reactivex.Single
import mx.kinich49.itemtracker.entities.apis.extensions.toDBModel
import mx.kinich49.itemtracker.entities.apis.models.CategoryResponse
import mx.kinich49.itemtracker.entities.apis.services.CategoryService
import mx.kinich49.itemtracker.entities.database.daos.CategoryDao
import mx.kinich49.itemtracker.entities.database.models.Category

class DownloadCategoriesUseCase(
    private val categoryService: CategoryService,
    private val categoryDao: CategoryDao
) {

    fun execute(): Completable {
        return downloadCategories()
            .flatMap { categoryResponses ->
                Single.defer {
                    mapResponseToDatabaseModel(categoryResponses)
                }
            }
            .flatMapCompletable { categories ->
                Completable.defer {
                    persistCategories(categories)
                }
            }
    }

    private fun downloadCategories(): Single<List<CategoryResponse>> {
        return categoryService.getCategories()
            .flatMap {
                Single.just(it.data)
            }
    }

    private fun mapResponseToDatabaseModel(response: List<CategoryResponse>): Single<List<Category>> {
        return Single.create { emitter ->
            if (!response.isNullOrEmpty()) {
                val dbModels = response.map {
                    it.toDBModel()
                }
                emitter.onSuccess(dbModels)
            } else {
                emitter.onError(RuntimeException("List was null"))
            }
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
package mx.kinich49.itemtracker.entities.usecases.sync.downstream

import io.reactivex.Completable

class DownstreamSynUseCase(
    private val brandsUseCase: DownloadBrandsUseCase,
    private val categoriesUseCase: DownloadCategoriesUseCase,
    private val storesUseCase: DownloadStoresUseCase,
    private val itemsUseCase: DownloadItemsUseCase
) {

    fun execute(): Completable {
        return brandsUseCase.execute()
            .andThen(Completable.defer {
                categoriesUseCase.execute()
            })
            .andThen(Completable.defer {
                storesUseCase.execute()
            })
            .andThen(Completable.defer {
                itemsUseCase.execute()
            })
    }

}
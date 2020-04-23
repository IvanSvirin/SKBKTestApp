package com.example.isvirin.skbktestapp.domain.di

import com.example.isvirin.skbktestapp.data.SharedPreferencesStore
import com.example.isvirin.skbktestapp.data.datasource.ContactCacheDataSource
import com.example.isvirin.skbktestapp.data.datasource.ContactRemoteDataSource
import com.example.isvirin.skbktestapp.data.repository.ContactRepositoryImpl
import com.example.isvirin.skbktestapp.datasource.cache.ContactCacheDataSourceImpl
import com.example.isvirin.skbktestapp.datasource.cache.ReactiveCache
import com.example.isvirin.skbktestapp.datasource.model.ContactEntity
import com.example.isvirin.skbktestapp.datasource.remote.ContactRemoteDataSourceImpl
import com.example.isvirin.skbktestapp.datasource.remote.ContactsApi
import com.example.isvirin.skbktestapp.datasource.remote.createNetworkClient
import com.example.isvirin.skbktestapp.domain.repository.ContactRepository
import com.example.isvirin.skbktestapp.domain.usecase.ContactUseCase
import com.example.isvirin.skbktestapp.domain.usecase.ContactsUseCase
import com.example.isvirin.skbktestapp.ui.details.DetailsViewModel
import com.example.isvirin.skbktestapp.ui.list.ListViewModel
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule,
        cacheModule,
        sharedPreferencesStoreModule
    )
}

val viewModelModule: Module = module {
    viewModel { ListViewModel(contactsUseCase = get()) }
    viewModel { DetailsViewModel(contactUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { ContactsUseCase(contactRepository = get()) }
    factory { ContactUseCase(contactRepository = get()) }
}

val repositoryModule: Module = module {
    single {
        ContactRepositoryImpl(
            cacheDataSource = get(),
            remoteDataSource = get(),
            sharedPreferencesStore = get()
        ) as ContactRepository
    }
}

val dataSourceModule: Module = module {
    single { ContactCacheDataSourceImpl(cache = get(CONTACT_CACHE)) as ContactCacheDataSource }
    single { ContactRemoteDataSourceImpl(api = plantsApi) as ContactRemoteDataSource }
}

val networkModule: Module = module {
    single { plantsApi }
}

val cacheModule: Module = module {
    single(name = CONTACT_CACHE) { ReactiveCache<List<ContactEntity>>() }
}

private const val BASE_URL = "https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/"

private val retrofit: Retrofit = createNetworkClient(
    BASE_URL,
    BuildConfig.DEBUG
)

private val plantsApi: ContactsApi = retrofit.create(ContactsApi::class.java)

private const val CONTACT_CACHE = "CONTACT_CACHE"

val sharedPreferencesStoreModule: Module = module {
    single { SharedPreferencesStore(androidContext()) }
}


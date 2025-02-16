package com.newmaziar.cryptopancake.di

import com.newmaziar.cryptopancake.crypto.data.CryptoRepositoryImp
import com.newmaziar.cryptopancake.crypto.data.local.LocalDataStore
import com.newmaziar.cryptopancake.crypto.data.local.LocalDataStoreImp
import com.newmaziar.cryptopancake.crypto.data.network.Api
import com.newmaziar.cryptopancake.crypto.data.network.ApiService.provideConverterFactory
import com.newmaziar.cryptopancake.crypto.data.network.ApiService.provideHttpClient
import com.newmaziar.cryptopancake.crypto.data.network.ApiService.provideRetrofit
import com.newmaziar.cryptopancake.crypto.data.network.ApiService.provideService
import com.newmaziar.cryptopancake.crypto.data.remote.RemoteDataSource
import com.newmaziar.cryptopancake.crypto.data.remote.RemoteDataSourceImpl
import com.newmaziar.cryptopancake.crypto.domain.CryptoRepository
import com.newmaziar.cryptopancake.crypto.view.CryptoViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit


val appModule = module {
    // Network
    single { get<Retrofit>().create(Api::class.java) }
    single { provideHttpClient() }
    single { provideConverterFactory() }
    single { provideRetrofit(get(), get()) }
    single { provideService(get()) }

    // Repository
    singleOf(::LocalDataStoreImp).bind<LocalDataStore>()
    single { RemoteDataSourceImpl(get()) }.bind<RemoteDataSource>()
    single { CryptoRepositoryImp(get(), get()) }.bind<CryptoRepository>()

    // ViewModel
    viewModelOf(::CryptoViewModel)
}
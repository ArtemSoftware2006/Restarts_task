package com.example.firstapp.di

import com.example.firstapp.data.SupabaseClientInstance
import com.example.firstapp.data.repository.SupabaseDishRepository
import com.example.firstapp.data.repository.SupabaseOrderRepository
import com.example.firstapp.data.repository.SupabaseReservationRepository
import com.example.firstapp.domain.repository.DishRepository
import com.example.firstapp.domain.repository.OrderRepository
import com.example.firstapp.domain.repository.ReservationRepository
import com.example.firstapp.domain.usecase.AddOrderItemUseCase
import com.example.firstapp.domain.usecase.CreateOrderUseCase
import com.example.firstapp.domain.usecase.CreateReservationUseCase
import com.example.firstapp.domain.usecase.GetActiveDishesUseCase
import com.example.firstapp.domain.usecase.GetFreeTablesUseCase
import com.example.firstapp.domain.usecase.GetOrderItemsUseCase
import com.example.firstapp.domain.usecase.RemoveOrderItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient =
        SupabaseClientInstance.client

    @Provides
    @Singleton
    fun provideReservationRepository(
        supabase: SupabaseClient
    ): ReservationRepository =
        SupabaseReservationRepository(supabase)

    @Provides
    @Singleton
    fun provideDishesRepository(
        supabase: SupabaseClient
    ): DishRepository =
        SupabaseDishRepository(supabase)

    @Provides
    @Singleton
    fun provideOrderRepository(
        supabase: SupabaseClient
    ): OrderRepository =
        SupabaseOrderRepository(supabase)

    @Provides
    @Singleton
    fun provideGetFreeTablesUseCase(
        repo: ReservationRepository
    ): GetFreeTablesUseCase =
        GetFreeTablesUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateReservationUseCase(
        repo: ReservationRepository
    ): CreateReservationUseCase =
        CreateReservationUseCase(repo)

    @Provides
    @Singleton
    fun provideGetActiveDishesUseCase(
        repo: DishRepository
    ): GetActiveDishesUseCase =
        GetActiveDishesUseCase(repo)

    @Provides
    @Singleton
    fun provideCreateOrderUseCase(
        repo: OrderRepository
    ): CreateOrderUseCase =
        CreateOrderUseCase(repo)

    @Provides
    @Singleton
    fun provideGetOrderItemsUseCase(
        repo: OrderRepository
    ): GetOrderItemsUseCase =
        GetOrderItemsUseCase(repo)

    @Provides
    @Singleton
    fun provideAddOrderItemUseCase(
        repo: OrderRepository
    ): AddOrderItemUseCase =
        AddOrderItemUseCase(repo)

    @Provides
    @Singleton
    fun provideRemoveOrderItemUseCase(
        repo: OrderRepository
    ): RemoveOrderItemUseCase =
        RemoveOrderItemUseCase(repo)
}

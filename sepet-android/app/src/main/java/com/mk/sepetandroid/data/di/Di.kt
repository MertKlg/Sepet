package com.mk.sepetandroid.data.di

import android.content.Context
import androidx.room.Room
import com.mk.sepetandroid.common.Constants
import com.mk.sepetandroid.data.impl.AddressImpl
import com.mk.sepetandroid.data.impl.CartImpl
import com.mk.sepetandroid.data.impl.CategoryImpl
import com.mk.sepetandroid.data.impl.OrderImpl
import com.mk.sepetandroid.data.impl.PasswordImpl
import com.mk.sepetandroid.data.impl.ProductImpl
import com.mk.sepetandroid.data.impl.ProfileImpl
import com.mk.sepetandroid.data.impl.TokenDbImpl
import com.mk.sepetandroid.data.impl.AuthImpl
import com.mk.sepetandroid.data.local.SepetDatabase
import com.mk.sepetandroid.data.remote.AddressApi
import com.mk.sepetandroid.data.remote.CartApi
import com.mk.sepetandroid.data.remote.CategoryApi
import com.mk.sepetandroid.data.remote.OrderApi
import com.mk.sepetandroid.data.remote.PasswordApi
import com.mk.sepetandroid.data.remote.ProductApi
import com.mk.sepetandroid.data.remote.ProfileApi
import com.mk.sepetandroid.data.remote.AuthApi
import com.mk.sepetandroid.domain.repository.IAddressRepo
import com.mk.sepetandroid.domain.repository.ICartRepo
import com.mk.sepetandroid.domain.repository.ICategoryRepo
import com.mk.sepetandroid.domain.repository.IOrderRepo
import com.mk.sepetandroid.domain.repository.IPasswordRepo
import com.mk.sepetandroid.domain.repository.IProductRepo
import com.mk.sepetandroid.domain.repository.IProfileRepo
import com.mk.sepetandroid.domain.repository.ITokenDbRepo
import com.mk.sepetandroid.domain.repository.IAuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Di {

    @Singleton
    @Provides
    fun provideApi() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(Constants.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSepetDatabase(@ApplicationContext context : Context) : SepetDatabase{
         return Room.databaseBuilder(
             context,
             SepetDatabase::class.java,
             Constants.APPDATABASE
         ).build()
    }


    @Provides
    fun provideProfile(retrofit: Retrofit) : IProfileRepo{
        return ProfileImpl(retrofit.create(ProfileApi::class.java))
    }

    @Provides
    fun provideUser(retrofit: Retrofit) : IAuthRepo{
        return AuthImpl(retrofit.create(AuthApi::class.java))
    }

    @Provides
    fun provideCategory(retrofit: Retrofit) : ICategoryRepo{
        return CategoryImpl(retrofit.create(CategoryApi::class.java))
    }

    @Provides
    fun provideTokenDb(sepetDatabase : SepetDatabase) : ITokenDbRepo{
        return TokenDbImpl(sepetDatabase.tokenDao())
    }

    @Provides
    fun provideProduct(retrofit: Retrofit,database: SepetDatabase) : IProductRepo {
        return ProductImpl(retrofit.create(ProductApi::class.java))
    }

    @Provides
    fun provideAddress(retrofit: Retrofit) : IAddressRepo {
        return AddressImpl(retrofit.create(AddressApi::class.java))
    }

    @Provides
    fun provideCart(retrofit: Retrofit) : ICartRepo {
        return CartImpl(retrofit.create(CartApi::class.java))

    }

    @Provides
    fun provideOrder(retrofit: Retrofit) : IOrderRepo{
        return OrderImpl(retrofit.create(OrderApi::class.java))
    }

    @Provides
    fun providePassword(retrofit: Retrofit) : IPasswordRepo{
        return PasswordImpl(retrofit.create(PasswordApi::class.java))
    }



}
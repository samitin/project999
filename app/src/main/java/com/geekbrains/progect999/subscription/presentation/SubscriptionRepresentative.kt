package com.geekbrains.progect999.subscription.presentation

import android.util.Log
import androidx.annotation.MainThread
import com.geekbrains.progect999.core.App
import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.DispatchersList
import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.RunAsync
import com.geekbrains.progect999.core.UiObserver
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.Navigation
import com.geekbrains.progect999.main.Screen
import com.geekbrains.progect999.subscription.domain.SubscriptionInteractor
import com.geekbrains.progect999.subscription.domain.SubscriptionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface SubscriptionRepresentative :Representative<SubscriptionUiState>, SaveSubscriptionUiState,
    SubscriptionObserved, SubscriptionInner {
    fun init(restoreSubscriptionUiState: SaveAndRestoreSubscriptionUiState.Restore)
    @MainThread
    fun subscribe()
    suspend fun subscribeInternal()
    fun finish()
    fun comeback()

    class Base(
        private val runAsync: RunAsync,
        private val handleDeath: HandleDeath,
        private val observable: SubscriptionObservable,
        private val clear: ClearRepresentative,
        private val iteractor : SubscriptionInteractor,
        private val navigation: Navigation.Update,
        private val mapper: SubscriptionResult.Mapper) : Representative.Abstract<SubscriptionUiState>(runAsync),SubscriptionRepresentative {

       override fun observed() = observable.clear()

       override fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
               if (restoreState.isEmpty()){
                   handleDeath.firstOpening()
                   observable.update(SubscriptionUiState.Initial)
               }else {
                   if (handleDeath.didDeathHappen()) {
                       handleDeath.deathHandled()
                       val uiState = restoreState.restore()
                       uiState.restoreAfterDeath(this,observable) //todo

                   }
               }
       }

       override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) =
           observable.saveState(saveState)
        private var canGoBack = true
       override fun subscribe() {
           canGoBack = false
            observable.update(SubscriptionUiState.Loading)
            subscribeInner()
        }
        private val uiBlock: (SubscriptionResult) -> Unit = {result ->
            result.map(mapper){canGoBack = it}
        }
        override suspend fun subscribeInternal() = handleAsyncInternal({
            iteractor.subscribeInternal()
        },uiBlock)


        override fun subscribeInner() {
           handleAsync({
               iteractor.subscribe()
           },uiBlock)
       }

       override fun finish() {
           clear()
           clear.clear(SubscriptionRepresentative::class.java)
           navigation.update(Screen.Dashboard)
       }

        override fun comeback() {
            if (canGoBack) {
                finish()
            }
        }

        override fun startGettingUpdates(callBack: UiObserver<SubscriptionUiState>) =
           observable.updateObserver(callBack)

       override fun stopGettingUpdates() =
           observable.updateObserver(EmptySubscriptionObserver)

   }
}
object EmptySubscriptionObserver : SubscriptionObserver {
    override fun update(data: SubscriptionUiState) = Unit
}
interface SaveSubscriptionUiState{
    fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save)
}
interface SubscriptionObserved{
    fun observed()
}
interface SubscriptionInner{
    fun subscribeInner()
}
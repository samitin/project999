package com.geekbrains.progect999.subscription.presentation

import android.util.Log
import androidx.annotation.MainThread
import com.geekbrains.progect999.core.App
import com.geekbrains.progect999.core.ClearRepresentative
import com.geekbrains.progect999.core.HandleDeath
import com.geekbrains.progect999.core.Representative
import com.geekbrains.progect999.core.UiObserver
import com.geekbrains.progect999.dashboard.DashboardRepresentative
import com.geekbrains.progect999.main.Navigation
import com.geekbrains.progect999.main.Screen
import com.geekbrains.progect999.subscription.domain.SubscriptionInteractor

interface SubscriptionRepresentative :Representative<SubscriptionUiState>, SaveSubscriptionUiState,
    SubscriptionObserved, SubscriptionInner {


    fun init(restoreSubscriptionUiState: SaveAndRestoreSubscriptionUiState.Restore)
    @MainThread
    fun subscribe()

    fun finish()
   /* private val handleDeath = HandleDeath.Base()
    fun activityCreated(firstOpening : Boolean){
        if (firstOpening){
            handleDeath.firstOpening()
        }else{
            if (handleDeath.wasDeathHappened()){
                log("death happened смерть случилась")
                handleDeath.deathHandled()
            }else{
                log("just activity recreate просто воссоздание активности")
            }
        }
    }*/
    class Base(
       private val handleDeath: HandleDeath,
       private val observable: SubscriptionObservable,
       private val clear: ClearRepresentative,
       private val iteractor : SubscriptionInteractor,
       private val navigation: Navigation.Update) : SubscriptionRepresentative, () -> Unit {
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
       override fun subscribe() {
            observable.update(SubscriptionUiState.Loading)
            subscribeInner()
        }

       override fun subscribeInner() = iteractor.subscribe (this)

       override fun finish() {
           clear.clear(SubscriptionRepresentative::class.java)
           navigation.update(Screen.Dashboard)
       }

       override fun startGettingUpdates(callBack: UiObserver<SubscriptionUiState>) =
           observable.updateObserver(callBack)

       override fun stopGettingUpdates() =
           observable.updateObserver(EmptySubscriptionObserver)
       override fun invoke() {
            observable.update(SubscriptionUiState.Success)
       }
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
package com.geekbrains.progect999.subscription

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
import com.geekbrains.progect999.main.UserPremiumCache

interface SubscriptionRepresentative :Representative<SubscriptionUiState>,SaveSubscriptionUiState,SubscriptionObserved,SubscriptionInner {


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
        private val userPremiumCache:UserPremiumCache.Save,
        private val navigation: Navigation.Update) : SubscriptionRepresentative {
       override fun observed() = observable.clear()

       override fun init(restoreState: SaveAndRestoreSubscriptionUiState.Restore) {
               if (restoreState.isEmpty()){
                   handleDeath.firstOpening()
                   observable.update(SubscriptionUiState.Initial)
               }else {
                   if (handleDeath.didDeathHappen()) {

                       handleDeath.deathHandled()
                       log("SubscriptionRepresentative#restoreAfterDeath")
                       restoreState.restore().restoreAfterDeath(this,observable) //todo

                   }
               }
       }

       override fun saveState(saveState: SaveAndRestoreSubscriptionUiState.Save) {
           observable.saveState(saveState)
       }
       private fun thread() = Thread{
           Thread.sleep(10_000)
           userPremiumCache.saveUserPremium()
           observable.update(SubscriptionUiState.Success)
       }

       override fun subscribe() {
            observable.update(SubscriptionUiState.Loading)
            subscribeInner()
        }

       override fun subscribeInner() {
           log("SubscriptionRepresentative#subscribeInner")
           thread().start()
       }

       override fun finish() {
           clear.clear(DashboardRepresentative::class.java)
           clear.clear(SubscriptionRepresentative::class.java)
           navigation.update(Screen.Dashboard)
       }
       private fun log(msg : String){
           Log.d(App.TAG,msg)
       }

       override fun startGettingUpdates(callBack: UiObserver<SubscriptionUiState>) = observable.updateObserver(callBack)

       override fun stopGettingUpdates() = observable.updateObserver()



   }
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